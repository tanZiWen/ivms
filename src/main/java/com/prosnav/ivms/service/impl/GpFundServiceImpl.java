package com.prosnav.ivms.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import com.prosnav.ivms.model.GpFund;
import com.prosnav.ivms.repository.ivm.FieldPicker;
import com.prosnav.ivms.repository.ivm.CompanyInvestmentRepository;
import com.prosnav.ivms.repository.ivm.GpFundRepository;
import com.prosnav.ivms.service.CompanyService;
import com.prosnav.ivms.service.GpFundService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.GpFundView;

@Service
public class GpFundServiceImpl extends AbstractService implements GpFundService {
	@Autowired
	private GpFundRepository gpRepository;
	@Autowired
	private CompanyInvestmentRepository investmentRepository;
	
	@Autowired
	private CompanyService companyService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean addGp(GpFundView view) {
		try {
			GpFund entity = view.toModel();
			entity.setId(gpRepository.nextId(idg));
			Date now = new Date();
			entity.setCrt(now);
			entity.setLut(now);
			entity = gpRepository.save(entity);
			view.fromModel(entity);
			return true;
		} catch (Exception e) {
			logger.error("Failed to add gp", e);
		}
		return false;
	}

	@Override
	public Map<String, Object> listGp(PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage()-1, pv.getSize());
		Criteria condition = Criteria.where("deled").is(false);
		Page<GpFund> page = gpRepository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("companies", page.getContent());
		return result;
	}
	
	@Override
	public Map<String, Object> searchGp(String keyword, PageView pv) {
		GpFundView view = new GpFundView();
		view.setName(keyword);
		return searchGp(view, pv);
	}
	
	@Override
	public Map<String, Object> searchGp(GpFundView view, PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		
		Criteria condition = Criteria.where("deled").is(false);
		if(view != null) {
			if(!StringUtils.isEmpty(view.getName())) {
				condition.orOperator(
						Criteria.where("name").regex(Pattern.compile(view.getName(), Pattern.CASE_INSENSITIVE)),
						Criteria.where("alias").regex(Pattern.compile(view.getName(), Pattern.CASE_INSENSITIVE)));
			}
			if(!StringUtils.isEmpty(view.getGpFirmId())) {
				condition.and("gpFirmId").is(view.getGpFirmId());
			}
		}
		
		Page<GpFund> page = gpRepository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("companies", page.getContent());
		
		return result;
	}

	@Override
	public boolean updateGp(GpFundView view) {
		try {
			GpFund entity = view.toModel();
			entity.setLut(new Date());
			entity = gpRepository.save(entity);
			view.fromModel(entity);
			return true;
		} catch (Exception e) {
			logger.error("Failed to update gp fund", e);
		}
		return false;
	}

	@Override
	public boolean deleteGp(long id) {
		try {
			GpFund entity = gpRepository.findOne(id);
			if(entity != null) {
				entity.setDeled(true);
				entity.setLut(new Date());
				gpRepository.save(entity);
			}
			return true;
		} catch (Exception e) {
			logger.error("Failed to delete gp", e);
			return false;
		}
	}

	@Override
	public Map<Long, GpFundView> getAndMap(Condiction condiction) {
		Map<Long, GpFundView> map = new HashMap<Long, GpFundView>();

		if (condiction.getIds() == null || condiction.getIds().size() == 0) {
			return map;
		}

		Iterable<GpFund> companies = null;

		if (condiction.getFieldPicker() == null) {
			companies = gpRepository.findAll(condiction.getIds());
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

		for (GpFund gp : companies) {
			GpFundView view = new GpFundView();
			view.fromModel(gp);
			map.put(gp.getId(), view);
		}

		return map;
	}

	@Override
	public GpFundView get(Long id) {
		GpFund entity = gpRepository.findOne(id);
		GpFundView view = new GpFundView();
		view.fromModel(entity);
		return view;
	}
	
	@Override
	public List<GpFund> getGPFundByFirmId(Long gpFirmId) {
		return gpRepository.getGPFundByFirmId(gpFirmId);
	}

}
