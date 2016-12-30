/**
 * 
 */
package com.prosnav.ivms.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.prosnav.ivms.model.Adviser;
import com.prosnav.ivms.repository.ivm.AdviserRepository;
import com.prosnav.ivms.service.AdviserService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.AdviserView;

/**
 * @author wangnan
 *
 */
@Service
public class AdviserServiceImpl extends AbstractService implements AdviserService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AdviserRepository adviserRepository;

	/* (non-Javadoc)
	 * @see com.prosnav.ivms.service.AdviserService#list(java.util.Map, com.prosnav.ivms.util.PageView)
	 */
	@Override
	public Map<String, Object> list(Map<String, Object> params, PageView pv) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("list adviser");
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		
		Criteria condition = Criteria.where("deleted").is(false);
		if(params != null && params.get("name") != null) {
			String name = (String) params.get("name");
			if(!StringUtils.isEmpty(name)) {
				condition.and("name").regex(Pattern.compile(name, Pattern.CASE_INSENSITIVE));
			}
		}
		
		Page<Adviser> page = adviserRepository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		
		List<AdviserView> views = new ArrayList<AdviserView>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Adviser entity : page.getContent()) {
			AdviserView view = new AdviserView();
			view.fromModel(entity);
			view.setModifyTimeStr(sdf.format(entity.getModifyTime()));
			views.add(view);
		}
		
		result.put("views", views);
		result.put("pager", pv);
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.prosnav.ivms.service.AdviserService#save(com.prosnav.ivms.vo.AdviserView)
	 */
	@Override
	public boolean save(AdviserView adviserView) {
		
		Adviser adviser = adviserView.toModel();
		if(StringUtils.isEmpty(adviser.getId())) {
			adviser.setId(adviserRepository.nextId(idg));
			adviser.setCreateTime(new Date());
		}
		adviser.setModifyTime(new Date());
		adviser = adviserRepository.save(adviser);
		adviserView.fromModel(adviser);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.prosnav.ivms.service.AdviserService#get(java.lang.Long)
	 */
	@Override
	public AdviserView get(Long id) {
		Adviser adviser = adviserRepository.findOne(id);
		AdviserView adviserView = new AdviserView();
		adviserView.fromModel(adviser);
		return adviserView;
	}

	/* (non-Javadoc)
	 * @see com.prosnav.ivms.service.AdviserService#remove(java.lang.Long)
	 */
	@Override
	public boolean remove(Long id) {
		Adviser adviser = adviserRepository.findOne(id);
		if(adviser != null) {
			adviser.setDeleted(true);
			adviser.setModifyTime(new Date());
			adviserRepository.save(adviser);
		}
		return true;
	}

}
