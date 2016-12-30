/**
 * 
 */
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
import com.prosnav.ivms.model.Company.FinancingRound;
import com.prosnav.ivms.repository.ivm.CompanyInvestmentRepository;
import com.prosnav.ivms.service.CompanyService;
import com.prosnav.ivms.service.CompanyInvestmentService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.CompanyView;
import com.prosnav.ivms.vo.CompanyInvestmentView;

/**
 * @author wangnan
 *
 */
@Service
public class CompanyInvestmentServiceImpl extends AbstractService implements
		CompanyInvestmentService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CompanyInvestmentRepository repository;
	
	@Autowired
	private CompanyService companyService;
	
	/* (non-Javadoc)
	 * @see com.prosnav.ivms.service.GpFundInvestmentService#list(java.util.Map, com.prosnav.ivms.util.PageView)
	 */
	@Override
	public Map<String, Object> listCompanyInvestment(Map<String, Object> params, PageView pv) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("list CompanyInvestmentService");
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		
		Criteria condition = Criteria.where("deled").is(false);
		if(params != null) {
			if(params.get("view") != null) {
				System.out.println(params.get("view"));
				CompanyInvestmentView view = (CompanyInvestmentView) params.get("view");
				if(!StringUtils.isEmpty(view.getCompanyId())) {
					condition.and("companyId").is(view.getCompanyId());
				}else {
					if(!StringUtils.isEmpty(view.getCompanyName())) {
						condition.and("companyName").is(view.getCompanyName());
					}
				}
				if(!StringUtils.isEmpty(view.getFinancingRoundCode())) {
					condition.and("financingRoundCode").is(view.getFinancingRoundCode());
				}
				if(!StringUtils.isEmpty(view.getRelationRid())) {
					condition.and("relation.rid").is(view.getRelationRid())
							.and("relation.type").is(view.getRelationType());
				}
			}
		}
		Page<CompanyInvestment> page = repository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		List<CompanyInvestmentView> views = new ArrayList<CompanyInvestmentView>();
		for(CompanyInvestment entity : page.getContent()) {
			CompanyInvestmentView view = new CompanyInvestmentView();
			view.fromModel(entity);
			views.add(view);
		}
		
		result.put("views", views);
		result.put("pager", pv);
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.prosnav.ivms.service.GpFundInvestmentService#save(com.prosnav.ivms.vo.GpFundInvestmentView)
	 */
	@Override
	public boolean addCompanyInvestment(CompanyInvestmentView view) {
		CompanyInvestment entity = view.toModel();
		if(entity.getId() == null) {
			entity.setId(repository.nextId(idg));
			entity.setCrt(new Date());
		}
		entity.setLut(new Date());
		entity = repository.save(entity);
		view.fromModel(entity);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.prosnav.ivms.service.GpFundInvestmentService#get(java.lang.Long)
	 */
	@Override
	public CompanyInvestmentView getCompanyInvestment(Long id) {
		CompanyInvestmentView view = new CompanyInvestmentView();
		view.fromModel(repository.findOne(id));
		return view;
	}

	/* (non-Javadoc)
	 * @see com.prosnav.ivms.service.GpFundInvestmentService#remove(java.lang.Long)
	 */
	@Override
	public boolean removeCompanyInvestment(Long id) {
		CompanyInvestment entity = repository.findOne(id);
		if(entity != null) {
			entity.setDeled(true);
			repository.save(entity);
		}
		return true;
	}

	@Override
	public void mergeCompany(List<CompanyInvestmentView> views) {
		List<Long> ids = new ArrayList<Long>();
		for(CompanyInvestmentView v : views) {
			ids.add(v.getCompanyId());
		}
		Map<Long, CompanyView> companys = companyService.searchByIds(ids);
		for(CompanyInvestmentView v : views) {
			CompanyView cv = companys.get(v.getCompanyId());
			if(cv != null) {
				v.setCompanyView(cv);
				v.setCompanyName(cv.getName());
				if(cv.getFinancingRounds() != null) {
					for(FinancingRound fr : cv.getFinancingRounds()) {
						if(fr.getCode().equals(v.getFinancingRoundCode())) {
							v.setFinancingRound(fr);
							v.setFinancingRoundName(fr.getName());
						}
					}
				}
			}
		}
	}
	
	@Override    
	public List<CompanyInvestment> getCompanyInvestmentByFofId(Long id) {
		return repository.find(Criteria.where("fofId").is(id).and("deled").is(false), null);
	}
	
	@Override    
	public List<CompanyInvestment> getCompanyInvestmentByGPFundId(Long id) {
		return repository.find(Criteria.where("gpFundId").is(id).and("deled").is(false), null);
	}
}
