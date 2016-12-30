package com.prosnav.ivms;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.prosnav.ivms.core.IDGenerator;
import com.prosnav.ivms.core.IRedisIDGeneratorSupporter;
import com.prosnav.ivms.model.Person;
import com.prosnav.ivms.repository.ivm.PersonRepository;

/**
 * @author wangnan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class RepositoryTests {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired()
	@Qualifier("redisIdGenerator")
	private IDGenerator<IRedisIDGeneratorSupporter> idg;
	
	@Test
	public void test() {
		Person p = new Person();
		Long id = personRepository.nextId(idg);
		p.setId(id);
		p.setName("test");
		p = personRepository.save(p);
		System.out.println("p id : " + p.getId());
		Person p1 = personRepository.findOne(id);
		if(p1 == null || !p1.getName().equals("test")) {
			fail("insert person test fail.");
		}
		Page<Person> page = personRepository.findPage("{name : 'test'}", new PageRequest(1, 5, new Sort("name")));
		if(page.getNumber() < 1) {
			fail("person list should not empty");
		}
		p1.setName("test1");
		Person pu = personRepository.save(p1);
		if(!pu.getName().equals("test1")) {
			fail("update person test fail");
		}
		personRepository.delete(id);
		Person p2 = personRepository.findOne(id);
		if(p2 != null) {
			fail("delete person test fail");
		}
		
	}
}
