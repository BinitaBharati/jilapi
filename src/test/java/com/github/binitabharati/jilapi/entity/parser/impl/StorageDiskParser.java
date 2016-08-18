package com.github.binitabharati.jilapi.entity.parser.impl;

import java.util.HashMap;
import java.util.Map;

import com.github.binitabharati.jilapi.entity.parser.EntityParser;

public class StorageDiskParser implements EntityParser{

    public Map<String, Object> parse(String input) {
        // TODO Auto-generated method stub
        //input = "Disk: 0b.02.0 Shelf: 2   Bay: 0  Serial: HZ0BAPJL Vendor: NETAPP   Model: X302_HJUPI01TSSM Rev: NA04 RPM: 7200 WWN: 5:006:05ba00:310024 UID: 500605BA:00310024:00000000:00000000:00000000:00000000:00000000:00000000:00000000:00000000 Downrev: no Pri Port: A Sec Name: 5a.02.0 Sec Port: B Power-on Hours: N/A Blocks read:      0 Blocks written:   0 Time interval: 00:00:00 Glist count: 0 Scrub last done: 00:00:00 Scrub count: 0 LIP count: 0 Dynamically qualified: No Power cycle count: 0 Power cycle on error: 0 Current owner: 1573980176 Home owner: 1573980176 Reservation owner: 1573980176 Dongle Revision: 6E03"; 

                
        //System.out.println("HULLLO - entered with "+input);
        Map<String, Object> ret = new HashMap<String, Object>();
        input = input.trim();
        int curIdx = input.indexOf("Disk: ") + "Disk: ".length();
        String diskName = input.substring(curIdx, input.indexOf(" ", curIdx)).trim();
        ret.put("disk", diskName);
        curIdx = input.indexOf("Pri Port: ") + "Pri Port: ".length();
        ret.put("diskPort", input.substring(curIdx, input.indexOf(" ", curIdx)).trim());
        curIdx = input.indexOf("UID: ") + "UID: ".length();
        ret.put("uid", input.substring(curIdx, input.indexOf(" ", curIdx)).trim());
        curIdx = input.indexOf("Model: ") + "Model: ".length();
        ret.put("model", input.substring(curIdx, input.indexOf(" ", curIdx)).trim());
        curIdx = input.indexOf("Rev: ") + "Rev: ".length();
        ret.put("rev", input.substring(curIdx, input.indexOf(" ", curIdx)).trim());
        curIdx = input.indexOf("RPM: ") + "RPM: ".length();
        ret.put("rpm", input.substring(curIdx, input.indexOf(" ", curIdx)).trim());
        curIdx = input.indexOf("Serial: ") + "Serial: ".length();
        ret.put("sn", input.substring(curIdx, input.indexOf(" ", curIdx)).trim());
        curIdx = input.indexOf("Shelf: ") + "Shelf: ".length();
        ret.put("shelf", input.substring(curIdx, input.indexOf(" ", curIdx)).trim());
        curIdx = input.indexOf("Bay: ") + "Bay: ".length();
        ret.put("bay", input.substring(curIdx, input.indexOf(" ", curIdx)).trim());
        curIdx = input.indexOf("Vendor: ") + "Vendor: ".length();
        ret.put("vendor", input.substring(curIdx, input.indexOf(" ", curIdx)).trim());
        curIdx = input.indexOf("Current owner: ") + "Current owner: ".length();
        ret.put("curOwner", input.substring(curIdx, input.indexOf(" ", curIdx)).trim());
        curIdx = input.indexOf("Home owner: ") + "Home owner: ".length();
        ret.put("homeOwner", input.substring(curIdx, input.indexOf(" ", curIdx)).trim());
        curIdx = input.indexOf("Reservation owner: ") + "Reservation owner: ".length();
        if (input.indexOf(" ", curIdx) != -1) {
            ret.put("resrvnOwner", input.substring(curIdx, input.indexOf(" ", curIdx)).trim());
        } else {
            ret.put("resrvnOwner", input.substring(curIdx).trim());
        }
            
        System.out.println(ret);
        return ret;
    }
    
    public static void main(String[] args) {
        StorageDiskParser sdp = new StorageDiskParser();
        sdp.parse(null);
    }

}
