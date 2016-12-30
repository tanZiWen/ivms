package com.prosnav.ivms.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.prosnav.ivms.model.Fof;
import com.prosnav.ivms.repository.ivm.FieldPicker;
import com.prosnav.ivms.repository.ivm.FofRepository;
import com.prosnav.ivms.service.FofService;
import com.prosnav.ivms.util.PageView;

@Service
public class FofServiceImpl extends AbstractService implements FofService {
	@Autowired
	private FofRepository fofRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean addFof(Fof fof) {
		try {
			fof.setId(fofRepository.nextId(idg));
			Date now = new Date();
			fof.setCrt(now);
			fof.setLut(now);
			fofRepository.save(fof);
			return true;
		} catch (Exception e) {
			logger.error("Failed to add fof", e);
		}
		return false;
	}

	@Override
	public Map<String, Object> listFof(PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage()-1, pv.getSize());
		Criteria condition = Criteria.where("deled").is(false);
		Page<Fof> page = fofRepository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("companies", page.getContent());
		return result;
	}
	
	@Override
	public Map<String, Object> searchFof(String keyword, PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		
		Page<Fof> page = fofRepository.searchPage(keyword, pr);
		pv.setCount(page.getTotalElements());
		
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("companies", page.getContent());
		
		return result;
	}

	@Override
	public boolean updateFof(Fof fof) {
		try {
			fof.setLut(new Date());
			fofRepository.save(fof);
			return true;
		} catch (Exception e) {
			logger.error("Failed to update fof", e);
		}
		return false;
	}

	@Override
	public boolean deleteFof(long id) {
		try {
			Fof fof = fofRepository.findOne(id);
			if (fof != null) {
				fof.setDeled(true);
				fof.setLut(new Date());
				fofRepository.save(fof);
			}
			return true;
		} catch (Exception e) {
			logger.error("Failed to delete fof", e);
			return false;
		}
	}

	@Override
	public Map<Long, Fof> getAndMap(Condiction condiction) {
		Map<Long, Fof> map = new HashMap<Long, Fof>();

		if (condiction.getIds() == null || condiction.getIds().size() == 0) {
			return map;
		}

		Iterable<Fof> companies = null;

		if (condiction.getFieldPicker() == null) {
			companies = fofRepository.findAll(condiction.getIds());
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

		for (Fof fof : companies) {
			map.put(fof.getId(), fof);
		}

		return map;
	}

	@Override
	public Fof getFof(Long id) {
		return fofRepository.findOne(id);
	}
	
	@Override
	public List<Fof> getFofByFirmId(Long id) {
		return fofRepository.getFofByFirmId(id);
	}
}
