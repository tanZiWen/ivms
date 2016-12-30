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
import org.springframework.util.StringUtils;

import com.prosnav.ivms.controller.helper.Reference.Condiction;
import com.prosnav.ivms.model.FofFirm;
import com.prosnav.ivms.repository.ivm.FieldPicker;
import com.prosnav.ivms.repository.ivm.FofFirmRepository;
import com.prosnav.ivms.service.FofFirmService;
import com.prosnav.ivms.util.PageView;

@Service
public class FofFirmServiceImpl extends AbstractService implements FofFirmService {
	@Autowired
	private FofFirmRepository fofFirmRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean addFofFirm(FofFirm fofFirm) {
		try {
			fofFirm.setId(fofFirmRepository.nextId(idg));
			Date now = new Date();
			fofFirm.setCrt(now);
			fofFirm.setLut(now);
			fofFirmRepository.save(fofFirm);
			return true;
		} catch (Exception e) {
			logger.error("Failed to add fofFirm", e);
		}
		return false;
	}

	@Override
	public Map<String, Object> listFofFirm(Map<String, Object> params, PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage()-1, pv.getSize());
		Criteria condition = Criteria.where("deled").is(false);
		String name = (String) params.get("name");
		if (!StringUtils.isEmpty(name)) {
			condition.and("name").regex(name);
		}
		Page<FofFirm> page = fofFirmRepository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("companies", page.getContent());
		return result;
	}
	
	@Override
	public FofFirm getFofFirm(Long id) {
		return fofFirmRepository.findOne(id);
	}
	
	@Override
	public Map<String, Object> searchFofFirm(String keyword, PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		
		Page<FofFirm> page = fofFirmRepository.searchPage(keyword, pr);
		pv.setCount(page.getTotalElements());
		
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("companies", page.getContent());
		
		return result;
	}

	@Override
	public boolean updateFofFirm(FofFirm fofFirm) {
		try {
			fofFirm.setLut(new Date());
			fofFirmRepository.save(fofFirm);
			return true;
		} catch (Exception e) {
			logger.error("Failed to update fofFirm", e);
		}
		return false;
	}

	@Override
	public boolean deleteFofFirm(long id) {
		try {
			FofFirm fofFirm = fofFirmRepository.findOne(id);
			if(fofFirm != null) {
				fofFirm.setDeled(true);
				fofFirm.setLut(new Date());
				fofFirmRepository.save(fofFirm);
			}
			return true;
		} catch (Exception e) {
			logger.error("Failed to delete fofFirm", e);
			return false;
		}
	}

	@Override
	public Map<Long, FofFirm> getAndMap(Condiction condiction) {
		Map<Long, FofFirm> map = new HashMap<Long, FofFirm>();

		if (condiction.getIds() == null || condiction.getIds().size() == 0) {
			return map;
		}

		Iterable<FofFirm> companies = null;

		if (condiction.getFieldPicker() == null) {
			companies = fofFirmRepository.findAll(condiction.getIds());
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

		for (FofFirm fofFirm : companies) {
			map.put(fofFirm.getId(), fofFirm);
		}

		return map;
	}

}
