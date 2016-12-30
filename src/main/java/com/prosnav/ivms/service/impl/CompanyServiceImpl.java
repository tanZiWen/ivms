package com.prosnav.ivms.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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

import com.prosnav.ivms.controller.helper.Reference;
import com.prosnav.ivms.controller.helper.Reference.Condiction;
import com.prosnav.ivms.controller.helper.ReferenceFinder;
import com.prosnav.ivms.model.Company;
import com.prosnav.ivms.model.ModelType;
import com.prosnav.ivms.repository.ivm.CompanyRepository;
import com.prosnav.ivms.repository.ivm.FieldPicker;
import com.prosnav.ivms.service.CompanyService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.CompanyView;

@Service
public class CompanyServiceImpl extends AbstractService implements CompanyService {
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private ReferenceFinder referenceFinder;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public CompanyView getCompany(Long id) {
		CompanyView cv = new CompanyView();
		cv.fromModel(companyRepository.findOne(id));
		return cv;
	}
	
	@Override
	public boolean addCompany(Company company) {
		try {
			Long id = companyRepository.nextId(idg);
			company.setId(id);
			company.setListedCode(id.toString());
			Date now = new Date();
			company.setCrt(now);
			company.setLut(now);
			companyRepository.save(company);
			return true;
		} catch (Exception e) {
			logger.error("Failed to add company", e);
		}
		return false;
	}

	@Override
	public Map<String, Object> listCompany(PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage()-1, pv.getSize());
		Criteria condition = Criteria.where("deled").is(false);
		Page<Company> page = companyRepository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("companies", page.getContent());
		return result;
	}
	
	@Override
	public Map<String, Object> searchCompany(String keyword, PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		
		Page<Company> page = companyRepository.searchPage(keyword, pr);
		pv.setCount(page.getTotalElements());
		
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("companies", page.getContent());
		
		return result;
	}

	@Override
	public boolean updateCompany(Company company) {
		try {
			company.setLut(new Date());
			companyRepository.save(company);
			return true;
		} catch (Exception e) {
			logger.error("Failed to update company", e);
		}
		return false;
	}

	@Override
	public boolean deleteCompany(long id) {
		try {
			Company c = companyRepository.findOne(id);
			if(c != null) {
				c.setDeled(true);
				c.setLut(new Date());
				companyRepository.save(c);
			}
			return true;
		} catch (Exception e) {
			logger.error("Failed to delete company", e);
			return false;
		}
	}

	@Override
	public Map<Long, Company> getAndMap(Condiction condiction) {
		Map<Long, Company> map = new HashMap<Long, Company>();

		if (condiction.getIds() == null || condiction.getIds().size() == 0) {
			return map;
		}

		Iterable<Company> companies = null;

		if (condiction.getFieldPicker() == null) {
			companies = companyRepository.findAll(condiction.getIds());
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

		for (Company company : companies) {
			map.put(company.getId(), company);
		}

		return map;
	}

	@Override
	public Map<String, Object> list(CompanyView cv, PageView pv) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("list company");
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		
		Criteria condition = Criteria.where("deled").is(false);
		if(cv != null) {
			if(!StringUtils.isEmpty(cv.getName())) {
				condition.orOperator(
						Criteria.where("alias").regex(Pattern.compile(cv.getName(), Pattern.CASE_INSENSITIVE)),
						Criteria.where("name").regex(Pattern.compile(cv.getName(), Pattern.CASE_INSENSITIVE))
						);
			}
		}
		
		Page<Company> page = companyRepository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		
		List<CompanyView> views = new ArrayList<CompanyView>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Reference reference = new Reference();
		for(Company entity : page.getContent()) {
			CompanyView view = new CompanyView();
			view.fromModel(entity);
			view.setLutStr(sdf.format(entity.getLut()));
			views.add(view);
			reference.with(ModelType.PERSON, view.getLegalRepresentative());
		}
		Map<String, Object> personList = referenceFinder.find(reference);
		result.put("reference", personList);
		result.put("companies", views);
		result.put("pager", pv);
		
		return result;
	}

	@Override
	public Map<Long, CompanyView> searchByIds(Collection<Long> ids) {
		Map<Long, CompanyView> views = new HashMap<Long, CompanyView>();
		List<Company> entitys = null;
		if(ids != null && ids.size() != 0) {
			entitys = companyRepository.find(Criteria.where("deled").is(false).and("id").in(ids), null);
		}
		if(entitys != null) {
			for(Company c : entitys) {
				CompanyView v = new CompanyView();
				v.fromModel(c);
				views.put(v.getId(), v);
			}
		}
		return views;
	}

}
