package com.prosnav.ivms.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prosnav.ivms.controller.helper.Reference;
import com.prosnav.ivms.controller.helper.Reference.Condiction;
import com.prosnav.ivms.controller.helper.ReferenceFinder;
import com.prosnav.ivms.model.Meeting;
import com.prosnav.ivms.model.Meeting.Topic;
import com.prosnav.ivms.model.ModelType;
import com.prosnav.ivms.model.Person;
import com.prosnav.ivms.model.Person.WorkExperience;
import com.prosnav.ivms.model.Relation;
import com.prosnav.ivms.repository.ivm.FieldPicker;
import com.prosnav.ivms.service.MeetingService;
import com.prosnav.ivms.service.PersonService;
import com.prosnav.ivms.util.PageView;

@Controller
public class MeetingController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MeetingService meetingService;

	@Autowired
	private PersonService personService;

	@Autowired
	private ReferenceFinder referenceFinder;

	@RequestMapping(value = "/meeting", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getMeetingList(ModelMap model,
			HttpServletRequest request) {
		logger.debug("get person list");
		PageView page = new PageView(request);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = meetingService.listMeeting(page);
			@SuppressWarnings("unchecked")
			List<Meeting> meetings = (List<Meeting>) result.get("meetings");
			if (meetings == null) {
				return result;
			}

			Set<Long> participantIds = new HashSet<Long>();
			for (Meeting meeting : meetings) {
				if (meeting.getParticipants() != null) {
					participantIds.addAll(meeting.getParticipants());
				}
			}

			result.put("reference", referenceFinder.find(new Reference().with(
					ModelType.PERSON, participantIds)));
			return result;
		} catch (Exception e) {
			logger.error("failed to get meeting list", e);
			result.put("code", 505);
			return result;
		}
	}

	@RequestMapping(value = "/meeting/search", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> searchRelation(
			@RequestParam String keyword, @RequestParam int type, @RequestParam String name, HttpServletRequest request) {

		logger.debug("search meeting by keyword\t" + keyword);
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			result = meetingService.searchMeeting(keyword, type, name,
					new PageView(request));
			if (result != null && result.get("meetings") != null) {
				List<Meeting> meetings = (List<Meeting>) result.get("meetings");

				result.put(Reference.KEY, referenceFinder
						.findMeetingParticipants(meetings, FieldPicker.INCLUDE,
								Person.FIELDS_MINIMAL));
			}

			return result;
		} catch (Exception e) {
			logger.error("failed to seach meeting" + e.getCause());
			result = new HashMap<String, Object>();
			result.put("code", 505);
			return result;
		}
	}

	@RequestMapping(value = "/meeting/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getMeetingList(
			@PathVariable int id, ModelMap model) {
		logger.debug("get meeting ", id);
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			Meeting meeting = meetingService.getMeeting(id);
			if (meeting == null) {
				return result;
			}

			result.put("meeting", meeting);

			Set<Long> participantIds = new HashSet<Long>();
			if (meeting.getParticipants() != null) {
				participantIds.addAll(meeting.getParticipants());
			}

			Map<Long, Person> persons = personService.getAndMap(new Condiction(
					ModelType.PERSON, participantIds));

			Reference reference = new Reference();
			for (Person person : persons.values()) {
				List<WorkExperience> wes = person.getWorkExperiences();

				if (wes != null && wes.size() > 0) {
					WorkExperience we = wes.get(0);

					if (we.getCorpType() != null
							&& we.getCorpType() != ModelType.OTHER.getCode()) {
						reference.with(we.getCorpType(), we.getCorpId());
					}
				}
			}

			for (Topic topic : meeting.getTopics()) {
				for (Relation relation : topic.getRelations()) {
					if (relation.getType() == null || relation.getRid() == null) {
						continue;
					}

					reference.with(relation.getType(), relation.getRid());
				}
			}

			Map<String, Object> refData = referenceFinder.find(reference);

			@SuppressWarnings("unchecked")
			Map<Long, Person> refPerson = (HashMap<Long, Person>) refData
					.get(ModelType.PERSON.getKey());

			if (refPerson != null) {
				refPerson.putAll(persons);
			} else {
				refData.put(ModelType.PERSON.getKey(), persons);
			}

			result.put("reference", refData);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("failed to get meeting", e.getCause(), e.getMessage());
			result.put("code", 505);
			return result;
		}
	}

	@RequestMapping(value = "/meeting", method = RequestMethod.POST)
	public @ResponseBody Object createMeeting(@RequestBody Meeting meeting,
			ModelMap model, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (meetingService.addMeeting(meeting)) {
				result.put("meeting", meeting);
			} else {
				result.put("code", 500);
				result.put("message", "failed to save person.");
			}
		} catch (Exception e) {
			logger.error("Failed to create person", e.getCause());
			result.put("code", 500);
			result.put("message", e.getCause());
		}

		return result;
	}

	@RequestMapping(value = "/meeting", method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> updateMeeting(
			@RequestBody Meeting meeting, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>();

		boolean ok = meetingService.updateMeeting(meeting);

		if (!ok) {
			result.put("code", 500);
		}

		return result;
	}

	@RequestMapping(value = "/meeting/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteMeeting(
			@PathVariable long id, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>();

		boolean ok = meetingService.deleteMeeting(id);

		if (!ok) {
			result.put("code", 500);
		}

		return result;
	}
}
