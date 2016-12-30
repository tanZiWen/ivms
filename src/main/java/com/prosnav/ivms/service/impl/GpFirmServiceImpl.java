package com.prosnav.ivms.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.prosnav.ivms.controller.helper.Reference.Condiction;
import com.prosnav.ivms.model.GpFirm;
import com.prosnav.ivms.repository.ivm.FieldPicker;
import com.prosnav.ivms.repository.ivm.GpFirmRepository;
import com.prosnav.ivms.service.GpFirmService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.GpFirmView;

@Service
public class GpFirmServiceImpl extends AbstractService implements GpFirmService {
	@Autowired
	private GpFirmRepository gpFirmRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean addGpFirm(GpFirm gpFirm) {
		try {
			gpFirm.setId(gpFirmRepository.nextId(idg));
			Date now = new Date();
			gpFirm.setCrt(now);
			gpFirm.setLut(now);
			gpFirmRepository.save(gpFirm);
			return true;
		} catch (Exception e) {
			logger.error("Failed to add gpFirm", e);
		}
		return false;
	}

	@Override
	public Map<String, Object> listGpFirm(PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage()-1, pv.getSize());
		Criteria condition = Criteria.where("deled").is(false);
		Page<GpFirm> page = gpFirmRepository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("companies", page.getContent());
		return result;
	}
	
	@Override
	public Map<String, Object> searchGpFirm(String keyword, PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		
		Page<GpFirm> page = gpFirmRepository.searchPage(keyword, pr);
		pv.setCount(page.getTotalElements());
		
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("companies", page.getContent());
		
		return result;
	}

	@Override
	public boolean updateGpFirm(GpFirm gpFirm) {
		try {
			gpFirm.setLut(new Date());
			gpFirmRepository.save(gpFirm);
			return true;
		} catch (Exception e) {
			logger.error("Failed to update gpFirm", e);
		}
		return false;
	}

	@Override
	public boolean deleteGpFirm(long id) {
		try {
			GpFirm entity = gpFirmRepository.findOne(id);
			if(entity != null) {
				entity.setDeled(true);
				entity.setLut(new Date());
				gpFirmRepository.save(entity);
			}
			return true;
		} catch (Exception e) {
			logger.error("Failed to delete gpFirm", e);
			return false;
		}
	}

	@Override
	public Map<Long, GpFirm> getAndMap(Condiction condiction) {
		Map<Long, GpFirm> map = new HashMap<Long, GpFirm>();

		if (condiction.getIds() == null || condiction.getIds().size() == 0) {
			return map;
		}

		Iterable<GpFirm> companies = null;

		if (condiction.getFieldPicker() == null) {
			companies = gpFirmRepository.findAll(condiction.getIds());
		} else {
			Query query = new Query();

			if (condiction.getFieldPicker() == FieldPicker.INCLUDE) {
				for (String field : condiction.getFields()) {
					query.fields().include(field);
				}
			} else {
				for (String field : condiction.getFields()) {
					query.fields().exclude(field);
				}
			}

			// TODO: execute query on MongoTemplate
		}

		for (GpFirm gpFirm : companies) {
			map.put(gpFirm.getId(), gpFirm);
		}

		return map;
	}

	@Override
	public GpFirmView get(Long id) {
		GpFirmView view = null;
		if(id != null) {
			GpFirm entity = gpFirmRepository.findOne(id);
			if(entity != null) {
				view = new GpFirmView();
				view.fromModel(entity);
			}
		} 
		return view;
	}

}
