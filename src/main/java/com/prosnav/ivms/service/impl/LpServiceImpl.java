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
import com.prosnav.ivms.model.Lp;
import com.prosnav.ivms.repository.ivm.FieldPicker;
import com.prosnav.ivms.repository.ivm.LpRepository;
import com.prosnav.ivms.service.LpService;
import com.prosnav.ivms.util.PageView;

@Service
public class LpServiceImpl extends AbstractService implements LpService {
	@Autowired
	private LpRepository lpRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean addLp(Lp lp) {
		try {
			lp.setId(lpRepository.nextId(idg));
			Date now = new Date();
			lp.setCrt(now);
			lp.setLut(now);
			lpRepository.save(lp);
			return true;
		} catch (Exception e) {
			logger.error("Failed to add lp", e);
		}
		return false;
	}

	@Override
	public Map<String, Object> listLp(PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage()-1, pv.getSize());
		Criteria condition = Criteria.where("deled").is(false);
		Page<Lp> page = lpRepository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("companies", page.getContent());
		return result;
	}
	
	@Override
	public Map<String, Object> searchLp(String keyword, PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		
		Page<Lp> page = lpRepository.searchPage(keyword, pr);
		pv.setCount(page.getTotalElements());
		
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("companies", page.getContent());
		
		return result;
	}

	@Override
	public boolean updateLp(Lp lp) {
		try {
			lp.setLut(new Date());
			lpRepository.save(lp);
			return true;
		} catch (Exception e) {
			logger.error("Failed to update lp", e);
		}
		return false;
	}

	@Override
	public boolean deleteLp(long id) {
		try {
			Lp lp = lpRepository.findOne(id);
			if(lp != null) {
				lp.setDeled(true);
				lp.setLut(new Date());
				lpRepository.save(lp);
			}
			return true;
		} catch (Exception e) {
			logger.error("Failed to delete lp", e);
			return false;
		}
	}

	@Override
	public Map<Long, Lp> getAndMap(Condiction condiction) {
		Map<Long, Lp> map = new HashMap<Long, Lp>();

		if (condiction.getIds() == null || condiction.getIds().size() == 0) {
			return map;
		}

		Iterable<Lp> companies = null;

		if (condiction.getFieldPicker() == null) {
			companies = lpRepository.findAll(condiction.getIds());
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

		for (Lp lp : companies) {
			map.put(lp.getId(), lp);
		}

		return map;
	}

}
