package com.prosnav.ivms.service.impl;

import java.util.ArrayList;
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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.prosnav.ivms.model.CompanyInvestment;
import com.prosnav.ivms.model.GpFundInvestment;
import com.prosnav.ivms.repository.ivm.GpFundInvestmentRepository;
import com.prosnav.ivms.service.GpFundInvestmentService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.CompanyInvestmentView;
import com.prosnav.ivms.vo.GpFundInvestmentView;

@Service
public class GpFundInvestmentServiceImpl extends AbstractService implements GpFundInvestmentService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GpFundInvestmentRepository repository;
	
	@Override
	public Map<String, Object> listGpFundInvestment(Map<String, Object> params, PageView pv) {
		if(logger.isDebugEnabled()) {
			logger.debug("list GpFundInvestmentService");
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		
		Criteria condition = Criteria.where("deled").is(false);
		
		if(params != null) {
			if(params.get("view") != null) {
				GpFundInvestmentView view = (GpFundInvestmentView) params.get("view");
				if(!StringUtils.isEmpty(view.getGpFundId())) {
					condition.and("gpFundId").is(view.getGpFundId());
				}else {
					if(!(StringUtils.isEmpty(view.getGpFundName()))) {
						condition.and("gpFundName").is(view.getGpFundName());
					}
				}
				condition.and("fofId").is(view.getFofId());
 			}
		}
		Page<GpFundInvestment> page = repository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		List<GpFundInvestmentView> views = new ArrayList<GpFundInvestmentView>();
		for(GpFundInvestment entity : page.getContent()) {
			GpFundInvestmentView view = new GpFundInvestmentView();
			view.fromModel(entity);
			views.add(view);
		}
		
		result.put("views", views);
		result.put("pager", pv);
		return result;
	}

	@Override
	public boolean addGpFundInvestment(GpFundInvestmentView view) {
		GpFundInvestment entity = view.toModel();
		if(entity.getId() == null) {
			entity.setId(repository.nextId(idg));
			entity.setCrt(new Date());
		}
		entity.setLut(new Date());
		entity = repository.save(entity);
		view.fromModel(entity);
		return true;
	}

	@Override
	public GpFundInvestmentView getGpFundInvestment(Long id) {
		GpFundInvestmentView view = new GpFundInvestmentView();
		view.fromModel(repository.findOne(id));
		return view;
	}

	@Override
	public boolean removeGpFundInvestment(Long id) {
		GpFundInvestment entity = repository.findOne(id);
		if(entity != null) {
			entity.setDeled(true);
			repository.save(entity);
		}
		return true;
	}
	
	@Override    
	public List<GpFundInvestment> getGpFundInvestmentByFofId(Long id) {
		return repository.find(Criteria.where("fofId").is(id).and("deled").is(false), null);
	}

}
