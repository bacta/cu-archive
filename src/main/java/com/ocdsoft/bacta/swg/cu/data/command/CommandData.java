package com.ocdsoft.bacta.swg.cu.data.command;

import com.ocdsoft.bacta.swg.shared.iff.datatable.DataTableRow;
import lombok.Getter;

/**
 * Created by kburkhardt on 2/7/15.
 */
public class CommandData {
 
    @Getter private final String commandName;
    @Getter private final int defaultPriority;
    @Getter private final String scriptHook;
    @Getter private final String failScriptHook;
    @Getter private final String cppHook;
    @Getter private final String cppFailHook;
    @Getter private final float defaultTime;
    @Getter private final String characterAbility;
    
    public CommandData(DataTableRow row) {
        this.commandName = row.get(0).getString();
        this.defaultPriority = row.get(1).getInt();
        this.scriptHook = row.get(2).getString();
        this.failScriptHook = row.get(3).getString();
        this.cppHook = row.get(4).getString();
        this.cppFailHook = row.get(5).getString();
        this.defaultTime = row.get(6).getFloat();
        this.characterAbility = row.get(7).getString();
        
        // TODO: Finish parsing
    }
}
