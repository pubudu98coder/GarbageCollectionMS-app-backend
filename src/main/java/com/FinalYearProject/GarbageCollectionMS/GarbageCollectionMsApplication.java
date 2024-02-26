package com.FinalYearProject.GarbageCollectionMS;

import com.FinalYearProject.GarbageCollectionMS.auth.AuthenticationService;
import com.FinalYearProject.GarbageCollectionMS.auth.RegisterRequest;
import com.FinalYearProject.GarbageCollectionMS.entity.users.HouseHolder;
import com.FinalYearProject.GarbageCollectionMS.entity.users.User;
import com.FinalYearProject.GarbageCollectionMS.repo.HouseHolderRepository;
import com.FinalYearProject.GarbageCollectionMS.repo.UserRepository;
import com.FinalYearProject.GarbageCollectionMS.entity.users.Role;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GarbageCollectionMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GarbageCollectionMsApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){

		return new ModelMapper();
	}
	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service, HouseHolderRepository houseHolderRepository, UserRepository userRepository
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstName("Pubudu")
					.lastName("Tharaka")
					.email("pubudu@gmail.com")
					.password("1234")
					.role(Role.ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());

			var houseHolder = RegisterRequest.builder()
					.firstName("Lilanka")
					.lastName("Sawan")
					.email("lilanka@gmail.com")
					.password("5678")
					.role(Role.HOUSE_HOLDER)
					.build();
			System.out.println("HouseHolder token: " + service.register(houseHolder).getAccessToken());
			HouseHolder houseHolder1=houseHolderRepository.findById(2).orElse(null);
			houseHolder1.setHouseNo("h1");
			houseHolderRepository.save(houseHolder1);
			User adminG=userRepository.findById(1).orElse(null);
			HouseHolder houseHolder2=houseHolderRepository.findByHouseNo("h1").orElse(null);
			System.out.println(houseHolder2.getFirstName());
			System.out.println(adminG.getFirstName());
		};
		}
	}