package com.millenium.devopsbuddy.test.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.millenium.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.millenium.devopsbuddy.backend.persistence.domain.backend.Role;
import com.millenium.devopsbuddy.backend.persistence.domain.backend.User;
import com.millenium.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.millenium.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.millenium.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.millenium.devopsbuddy.backend.persistence.repositories.UserRepository;
import com.millenium.devopsbuddy.enums.PlansEnum;
import com.millenium.devopsbuddy.enums.RolesEnum;
import com.millenium.devopsbuddy.utils.UsersUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class RepositoriesIntegrationTest {

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;
	

	@Before
	public void init() {
		assertNotNull(planRepository);
		assertNotNull(roleRepository);
		assertNotNull(userRepository);
	}
	
	@Test
	public void testCreateNewPlan() {
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		Plan retrievedPlan = planRepository.findById(PlansEnum.BASIC.getId()).get();
		assertNotNull(retrievedPlan);
	}
	
	@Test
	public void testCreateNewRole() {
		Role userRole = createRole(RolesEnum.BASIC);
		roleRepository.save(userRole);
		Role retrievedRole = roleRepository.findById(RolesEnum.BASIC.getId()).get();
		assertNotNull(retrievedRole);
	}
	
	@Test
	public void testCreateNewUser() throws Exception {
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		
		User basicUser = UsersUtils.createBasicUser();
		basicUser.setPlan(basicPlan);
		
		Role basicRole = createRole(RolesEnum.BASIC);
		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole(basicUser, basicRole);
		userRoles.add(userRole);
		
		basicUser.getUserRoles().addAll(userRoles);
		
		for (UserRole ur : userRoles) {
			roleRepository.save(ur.getRole());
		}
		
		basicUser = userRepository.save(basicUser);
		
		User newlyCreatedUser = userRepository.findById(basicUser.getId()).get();
		assertNotNull(newlyCreatedUser);
		assertTrue(newlyCreatedUser.getId() != 0);
		assertNotNull(newlyCreatedUser.getPlan());
		assertNotNull(newlyCreatedUser.getPlan().getId());
		
		Set<UserRole> newlyCreatedUserRoles = newlyCreatedUser.getUserRoles();
		
		for (UserRole ur : newlyCreatedUserRoles) {
			assertNotNull(ur.getRole());
			assertNotNull(ur.getRole().getId());
		}
	}
	
	//------------------------ Private methods
	
	private Plan createPlan(PlansEnum plansENum) {
		return new Plan(plansENum);
	}
	
	private Role createRole(RolesEnum rolesEnum) {
		return new Role(rolesEnum);
	}
	
}