package com.municipality.Controller;





import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.municipality.Model.testModel;

import java.util.ArrayList;

@RestController
@RequestMapping(path="/api")
public class testController {

   
    ArrayList<testModel> all = new ArrayList<>();

    @PostMapping("/add")
    @ResponseBody
    public testModel add(@RequestBody testModel tm)
    {
        all.add(tm);
        return tm;
    }

    @PostMapping("/ad")
    @ResponseBody
    public String addurl(@RequestParam Integer tID,@RequestParam String tName)
    {
        testModel t = new testModel();
        t.settID(tID);
        t.settName(tName);
        all.add(t);
        return "Response : Successfully Added";
    }


    @GetMapping("/view")
    @ResponseBody
    public Iterable<testModel> view()
    {
        return all;
    }

    @GetMapping("/view/{tID}")
    @ResponseBody
    public testModel viewID(@PathVariable Integer tID)
    {
        
        return all.get(0);

    }

    @DeleteMapping("delete/{tID}")
    @ResponseBody
    public String deleteID(@PathVariable Integer tID)
    {
        
        return"{Response : Deleted }";


    }


    @PutMapping("edit/{tID}")
    @ResponseBody
    public ResponseEntity<Object> edit(@RequestBody testModel TMO,@PathVariable Integer tID) {
       
        return ResponseEntity.noContent().build();
    }


    }





