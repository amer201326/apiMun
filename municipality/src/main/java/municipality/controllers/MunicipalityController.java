package municipality.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Data.AttFileTex;
import Data.AttachmentArchiveCitizen;
import Data.AttachmentServiceEmployee;
import Data.Citizen;
import Data.CitizenAccount;
import Data.CitizenRequest;
import Data.GetDB_Eman;
import Data.GetFromDB;
import Data.GetFromDBaraa;
import Data.MunicipalityInformation;
import Data.ServerResponse;
import Data.Service;
import Data.ServiceAttachmentName;
import Data.ServiceCitizen;
import Data.StepsAndDecsions;
import Data.UpLoadFiles;
import io.swagger.annotations.ApiOperation;


@RestController()
@RequestMapping("/mmapi")
public class MunicipalityController {

	
    static List<UpLoadFiles> files = new ArrayList<>();

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
	@RequestMapping(method = RequestMethod.GET, value = "/att")
	public List<ServiceAttachmentName> att(){
		List<ServiceAttachmentName> ser = GetFromDB.getAttavhmentByserviceById(1);
		return ser;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/doneCitizenServices")
	public List<ServiceCitizen> doneCitizenServices(@RequestParam int idCitizen){
		List<ServiceCitizen> donser = GetFromDBaraa.doneCitizenServices(idCitizen);
		return donser;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/notDoneCitizenServices")
	public List<ServiceCitizen> notDoneCitizenServices(@RequestParam int idCitizen){
		List<ServiceCitizen> notdoneser = GetFromDBaraa.notDoneCitizenServices(idCitizen);
		return notdoneser;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/MunicipalityInformation")
	public MunicipalityInformation MunicipalityInformation(){
		MunicipalityInformation muni = GetFromDBaraa.getHomePageDetails() ;
		return muni;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/Profile")
	public Citizen Profile(@RequestParam String username,@RequestParam String passWord){
		Citizen cit = GetFromDB.getCitizenAccount(username, passWord);
		return cit;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/StepsAndDesion")
	public List<StepsAndDecsions> StepsAndDesion(@RequestParam int idcitizen,@RequestParam int idSerCit,@RequestParam int idService){
		List<StepsAndDecsions> lis = GetFromDB.StepsAndDesion(idcitizen, idSerCit, idService);
		return lis;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/AddCitizenRequest")
	public void AddCitizenRequest(@RequestBody CitizenRequest citizenRequest){
		citizenRequest.addCitizenRequestToDB();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/UpdateCitizen")
	public void UpdateCitizen(@RequestBody Citizen citizen){
		//citizen.setAccount(account);
		citizen.updateCitizen();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/CitizenAtt")
	public List<AttachmentArchiveCitizen> CitizenAtt(@RequestParam int idCitizen){
		List<AttachmentArchiveCitizen> att = GetFromDB.getAttachmantsArchiveJustFile(idCitizen);
		for (AttachmentArchiveCitizen attachmentArchiveCitizen : att) {
			attachmentArchiveCitizen.inputForData = null;
		}
		return att;
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET,value = "/fileAtt")
	public String fileAtt(HttpServletRequest request, HttpServletResponse response, @RequestParam int idAtt)  {

		System.out.println("fileee");

		ServiceAttachmentName asn  = GetFromDB.getAttachmentService(idAtt);
		
			//get the mimetype
			String mimeType = URLConnection.guessContentTypeFromName(asn.getNameFile());
			if (mimeType == null) {
				//unknown mimetype so set the mimetype to application/octet-stream
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + asn.getNameFile() + "\""));
			try {
				FileCopyUtils.copy(asn.inputForData, response.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(asn.inputForData.toString());
		return asn.inputForData.toString();
			
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET,value = "/fileAttTex")
	public AttFileTex fileAttTex(@RequestParam int idAttTex)  {

		System.out.println("fileee");
		ServiceAttachmentName asn  = GetFromDB.getAttachmentService(idAttTex);
		AttFileTex af = new AttFileTex();
		af.setName(asn.getNameFile());
		try {
			byte[] b = new byte[asn.inputForData.available()];
			asn.inputForData.read(b);
		    af.setS( new String(b));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println();
		return af;
			
	}
	
	
	@RequestMapping(method = RequestMethod.GET,value = "/fileAttCit")
	public void fileAttCit(HttpServletRequest request, HttpServletResponse response, @RequestParam int idAtt)  {

		System.out.println("fileee");

		AttachmentArchiveCitizen asn  = GetFromDB.getAttachmentCitizen(idAtt);
		
			//get the mimetype
			String mimeType = URLConnection.guessContentTypeFromName(asn.getNameFile());
			if (mimeType == null) {
				//unknown mimetype so set the mimetype to application/octet-stream
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + asn.getNameFile() + "\""));
			try {
				FileCopyUtils.copy(asn.inputForData, response.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
	}
	
	
	@RequestMapping(method = RequestMethod.GET,value = "/fileAttEmp")
	public void fileAttEmp(HttpServletRequest request, HttpServletResponse response, @RequestParam int idAtt)  {

		System.out.println("fileee");

		AttachmentServiceEmployee asn  = GetFromDB.getAttachmentSE(idAtt);
		
			//get the mimetype
			String mimeType = URLConnection.guessContentTypeFromName(asn.getFilename());
			if (mimeType == null) {
				//unknown mimetype so set the mimetype to application/octet-stream
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + asn.getFilename() + "\""));
			try {
				FileCopyUtils.copy(asn.inputForData, response.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
	}
	@RequestMapping(method = RequestMethod.GET,value = "/fileAttTexE")
	public AttFileTex fileAttTexE(@RequestParam int idAttTex)  {

		System.out.println("fileee");
		AttachmentServiceEmployee asn  = GetFromDB.getAttachmentSE(idAttTex);
		AttFileTex af = new AttFileTex();
		af.setName(asn.getFilename());
		try {
			byte[] b = new byte[asn.inputForData.available()];
			asn.inputForData.read(b);
		    af.setS( new String(b));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println();
		return af;
			
	}
	@RequestMapping(method = RequestMethod.POST, value = "/test")
	public void test(@RequestBody String s){
		//System.out.println("s="+s);
		
		byte[] bytes = s.getBytes();
		
		File newFile = new File("/Users/baraakali/Desktop/testt.jpg");
	      try {
	    	  
			 newFile.createNewFile();
		      FileOutputStream fOut = new FileOutputStream(newFile);
		      fOut.write(bytes);
		      fOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	}
	
	@RequestMapping(method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE , value = "/updateprofile")
	public ServerResponse updateprofile(@RequestParam("file") MultipartFile file,@RequestParam int idSer,@RequestParam int idCit,@RequestParam int idMax,@RequestParam String form){
		System.out.println("idSer = "+idSer);
		String s;
		File newFile = new File("/Users/baraakali/Desktop/testt.jpg");
		  try {
			  
			  newFile.createNewFile();
		      FileOutputStream fOut = new FileOutputStream(newFile);
		      fOut.write(file.getBytes());
		      fOut.close();
		      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  ServerResponse o = new ServerResponse();
		  o.setMessage("ddd");
		  o.setSuccess(true);
		return o;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/applyService")
	public void applyService(@RequestBody String note,@RequestParam int idSer,@RequestParam int idCit,@RequestParam int idMax){
		System.out.println(note+" bbb "+idSer+" bbb  "+idCit+"  bb  "+idMax);
		GetFromDB.addNewServiceCitizen(idMax,idSer,idCit,note);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/idMaxServCit")
	public Integer idMaxServCit(@RequestParam int idCit){
		Integer idmax = GetFromDB.getMaxId_service_citizen(idCit);
		System.out.println(idmax);
		return idmax;
	}
}
