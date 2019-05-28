package municipality.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Data.GetFromDB;
import Data.Service;
import io.swagger.annotations.ApiOperation;

@RestController()
@RequestMapping("/mmapi")
public class MunicipalityController {

	
	
	ArrayList<String> all = new ArrayList<>();
	
	@RequestMapping(method = RequestMethod.GET, value = "/all")
	public ArrayList<String> all()
			throws Exception {
		all.add("alfdkaj");
		return all;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/services")
	public List<Service> services(){
		List<Service> ser = GetFromDB.getAllServices();
		return ser;
	}
}
