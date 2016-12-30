/**
 * 
 */
package com.prosnav.ivms.controller.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prosnav.ivms.controller.helper.Reference.Condiction;
import com.prosnav.ivms.model.Meeting;
import com.prosnav.ivms.model.ModelType;
import com.prosnav.ivms.model.Person;
import com.prosnav.ivms.model.Person.WorkExperience;
import com.prosnav.ivms.repository.ivm.FieldPicker;
import com.prosnav.ivms.service.CompanyService;
import com.prosnav.ivms.service.FofFirmService;
import com.prosnav.ivms.service.FofService;
import com.prosnav.ivms.service.GpFirmService;
import com.prosnav.ivms.service.GpFundService;
import com.prosnav.ivms.service.LpService;
import com.prosnav.ivms.service.PersonService;

/**
 * @author wuzixiu
 *
 */
@Component
public class ReferenceFinder {

	@Autowired
	private PersonService personService;
	
	@Autowired
	private FofService fofService;
	
	@Autowired
	private FofFirmService fofFirmService;
	
	@Autowired
	private GpFundService gpFundService;
	
	@Autowired
	private GpFirmService gpFirmService;
	
	@Autowired
	private LpService lpService;

	@Autowired
	private CompanyService companyService;
	
	

	public Map<String, Object> find(Reference reference) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (reference == null || reference.getCondictions().size() == 0) {
			return result;
		}

		for (Condiction condiction : reference.getCondictions().values()) {
			switch (condiction.getModelType()) {
			case PERSON:
				result.put(ModelType.PERSON.getKey(),
						personService.getAndMap(condiction));
				break;
			case FOF:
				result.put(ModelType.FOF.getKey(),
						fofService.getAndMap(condiction));
				break;
			case FOF_FIRM:
				result.put(ModelType.FOF_FIRM.getKey(),
						fofFirmService.getAndMap(condiction));
				break;
			case GP:
				result.put(ModelType.GP.getKey(),
						gpFundService.getAndMap(condiction));
				break;
			case GP_FIRM:
				result.put(ModelType.GP_FIRM.getKey(),
						gpFirmService.getAndMap(condiction));
				break;
			case LP:
				result.put(ModelType.LP.getKey(),
						lpService.getAndMap(condiction));
				break;
			case COMPANY:
				result.put(ModelType.COMPANY.getKey(),
						companyService.getAndMap(condiction));
				break;
			default:
				break;
			// TODO: find other references
			}
		}

		return result;
	}

	public Map<String, Object> findPersonLatestWorkExperience(
			List<Person> persons) {
		if (persons == null) {
			return null;
		}

		Reference ref = new Reference();

		for (Person person : persons) {
			List<WorkExperience> wes = person.getWorkExperiences();

			if (wes != null && wes.size() > 0) {
				WorkExperience we = wes.get(0);
				if (we.getCorpType() != null
						&& we.getCorpType() != ModelType.OTHER.getCode()) {
					ref.with(we.getCorpType(), we.getCorpId());
				}
			}
		}

		return find(ref);
	}
	
	public Map<String, Object> findMeetingParticipants(
			List<Meeting> meetings, FieldPicker fp, String... fields) {
		if (meetings == null) {
			return null;
		}
		
		Reference ref = new Reference();
		
		for (Meeting meeting : meetings) {
			List<Long> participants = meeting.getParticipants();
			
			if (participants != null && participants.size() > 0) {
				for(Long uid : participants) {
					ref.with(ModelType.PERSON, uid, fp, fields);
				}
			}
		}
		
		return find(ref);
	}
}
