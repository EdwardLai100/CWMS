/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * OWNER: EDWARD LAI
 * PROJECT DEVELOPER: EDWARD LAI
 * APPLICATION: CAR WASH MANAGEMENT SYSTEM
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package com.edsproject.cwms.service;

import com.edsproject.cwms.entity.cwmsDatabase;
import com.edsproject.cwms.repository.cwmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class repositoryService {
    private final cwmsRepository cwmsRepository;

    boolean _debug = true; //Next release to change it to read IO config for ease setting

    @Autowired //inject the cwmsRepository dependency into this service class.
    public repositoryService(cwmsRepository cwmsRepository) {
        this.cwmsRepository = cwmsRepository;
    }

    public void triggerQueueInsertion(String data) {
        cwmsDatabase cwmsDatabase = new cwmsDatabase();
        String stringTimeStamp = time.getCurrentDate();
        //data = "MODEL,PLATE,SEGMENT,TYPE,NOTES,2,STATE";
        if (data != null && !data.startsWith(",") && !data.startsWith("null")) {
            String[] values = data.split(",");
            if (_debug) System.out.println("repositoryService/triggerQueueInsertion/data=" + data);
            if (_debug) System.out.println("repositoryService/triggerQueueInsertion/data.length()=" + values.length);
            if (values.length == 7) { //This checking is to ensure the data format send in is fixed
                String model = values[0];
                String plate = values[1];
                String segment = values[2];
                String type = values[3];
                String notes = values[4];
                double adjustment = Double.parseDouble(values[5]);
                String state = values[6];

                cwmsDatabase.addEntry(stringTimeStamp, model, plate, segment, type, notes, adjustment, state);
                cwmsRepository.save(cwmsDatabase);
            }
        }
    }

    public String getAllRecords() {
        List<cwmsDatabase> records = cwmsRepository.findAll(); //A JPA findAll method that works like SELECT * FROM TABLE under cwmsDatabase class
        StringBuilder result = new StringBuilder();

        for (cwmsDatabase cwmsdatabase : records) {
            result.append(cwmsdatabase.toString()).append("\n"); //This toString is overridden in cwmsDatabase class
        }

        return result.toString();
    }

}