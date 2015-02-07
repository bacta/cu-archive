package com.ocdsoft.bacta.swg.cu.data.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.data.SharedFileLoader;
import com.ocdsoft.bacta.swg.shared.iff.IffReader;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.iff.datatable.DataTable;
import com.ocdsoft.bacta.swg.shared.iff.datatable.DataTableIffReader;
import com.ocdsoft.bacta.swg.shared.iff.datatable.DataTableRow;
import com.ocdsoft.bacta.tre.TreeFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kyle on 2/7/15.
 */
@Singleton
public class CommandTable implements SharedFileLoader {
    private final static Logger logger = LoggerFactory.getLogger(CommandTable.class);

    private final Map<String, CommandData> commandTable;
    private final Map<String, CommandData> groundCommandTable;
    private final Map<String, CommandData> spaceCommandTable;

    private final TreeFile treeFile;

    @Inject
    public CommandTable(TreeFile treeFile) {
        this.treeFile = treeFile;
        commandTable = new HashMap<>();
        groundCommandTable = new HashMap<>();
        spaceCommandTable = new HashMap<>();
        
        load();
    }

    private void load() {
        logger.trace("Loading commands.");

        loadTables("datatables/command/command_tables_shared.iff", commandTable);
        logger.debug(String.format("Loaded %d commands.", commandTable.size()));

        loadTables("datatables/command/command_tables_shared_ground.iff", groundCommandTable);
        logger.debug(String.format("Loaded %d ground commands.", groundCommandTable.size()));

        loadTables("datatables/command/command_tables_shared_space.iff", spaceCommandTable);
        logger.debug(String.format("Loaded %d space commands.", spaceCommandTable.size()));
    }

    /**
     * Load the file that contains the list of tables to load commands from 
     * @param tableFile path to datatable iff
     * @param table the map to load data into
     */
    private void loadTables(final String tableFile, final Map<String, CommandData> table) {

        try {
            final IffReader<DataTable> dataTableReader = new DataTableIffReader();
            final DataTable dataTable = dataTableReader.read(new ChunkReader(tableFile, treeFile.open(tableFile)));

            // Get referenced table names
            for (DataTableRow row : dataTable.getRows()) {
                String tableName = row.get(0).getString();
                loadTable(tableName, table);
            }
        } catch(Exception e) {
            logger.error("Unable to load commands file list from {}", tableFile);
        }
    }

    /**
     * Loads actual commands into map
     * @param commandTable iff datatable the commands are located in
     * @param table table to load the command data into
     */
    private void loadTable(final String commandTable, final Map<String, CommandData> table) {
        
        try {
            final IffReader<DataTable> dataTableReader = new DataTableIffReader();
            final DataTable dataTable = dataTableReader.read(new ChunkReader(commandTable, treeFile.open(commandTable)));

            // Get Shared Table names
            for (DataTableRow row : dataTable.getRows()) {
                CommandData newData = new CommandData(row);
                table.put(newData.getCommandName(), newData);
            }
        } catch(Exception e) {
            logger.error("Unable to load commands table from {}", commandTable);
        }
    }
    
    @Override
    public void reload() {
        synchronized (this) {
            
            commandTable.clear();
            groundCommandTable.clear();
            spaceCommandTable.clear();
            
            load();
        }
    }
}
