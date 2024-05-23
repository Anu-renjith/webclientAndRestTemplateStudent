package com.example.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/student")
public class StudentController {
	@Autowired
	public WebClient.Builder webClient;
	
	
	@GetMapping("/{studentId}")
	public ResponseEntity<ResponseData> getStudentDetails(@PathVariable Long studentId){
		ResponseData response=new ResponseData();
		Student s1=new Student();
		s1.setStudentId(1L);
		s1.setStudentName("anu");
		s1.setAdress("kottayam");
		s1.setCollegeId(1L); 
		response.setStudent(s1);
		long collegeId=s1.getCollegeId();
		//s1.setCollegeId(1L);  here we have to use rest template or web client
		/*RestTemplate restTemplate=new RestTemplate();
		String endpoint="http://localhost:8081/college/1";
	
		ResponseEntity<College> data=restTemplate.getForEntity(endpoint, College.class, collegeId);
		if(data.getStatusCodeValue()==200) {
			College c1=data.getBody();
			response.setCollege(c1);
		} */
		
		College c1=webClient.build()
		.get()
		.uri("http://localhost:8081/college/"+collegeId)
		.retrieve()
		.bodyToMono(College.class)
		.block();
		response.setCollege(c1);
		return new ResponseEntity<ResponseData>(response,HttpStatus.OK);
	}

}
