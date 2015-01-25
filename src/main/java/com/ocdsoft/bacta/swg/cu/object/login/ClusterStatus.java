package com.ocdsoft.bacta.swg.cu.object.login;

/**
 * Created by kburkhardt on 1/18/15.
 */

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.network.client.ServerStatus;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.Getter;
import org.joda.time.DateTimeZone;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 *
 struct LoginClusterStatus_ClusterData
 {
     unsigned int m_clusterId;
     std::string m_connectionServerAddress;
     unsigned __int16 m_connectionServerPort;
     unsigned __int16 m_connectionServerPingPort;
     int m_populationOnline;
     LoginClusterStatus_ClusterData::PopulationStatus m_populationOnlineStatus;
     int m_maxCharactersPerAccount;
     int m_timeZone;
     LoginClusterStatus_ClusterData::Status m_status;
     bool m_dontRecommend;
     unsigned int m_onlinePlayerLimit;
     unsigned int m_onlineFreeTrialLimit;
 }
 *
 */

public class ClusterStatus implements ByteBufferWritable {

    @Getter
    private final int clusterId;

    @Getter
    private final String name;

    @Getter
    private final String connectionServerAddress;

    @Getter
    private final short connectionServerPort;

    @Getter
    private final short connectionServerPingPort;

    @Getter
    private final int populationOnline;

    @Getter
    private final PopulationStatus populationOnlineStatus; //enum

    @Getter
    private final int maxCharactersPerAccount;

    @Getter
    private final int timeZone;

    @Getter
    private final ServerStatus status; //enum

    private final boolean dontRecommend;

    @Getter
    private final int onlinePlayerLimit;

    @Getter
    private final int onlineFreeTrialLimit;

    public ClusterStatus(BactaConfiguration configuration) {
        clusterId = configuration.getInt("Bacta/GameServer", "Id");
        name = configuration.getString("Bacta/GameServer", "Name");
        connectionServerAddress = configuration.getString("Bacta/GameServer", "PublicAddress");
        connectionServerPort = (short) configuration.getInt("Bacta/GameServer", "Port");
        connectionServerPingPort = (short) configuration.getInt("Bacta/GameServer", "Ping");
        populationOnline = 0;
        populationOnlineStatus = PopulationStatus.PS_very_light;
        maxCharactersPerAccount = configuration.getInt("Bacta/GameServer", "MaxCharsPerAccount");
        timeZone = DateTimeZone.getDefault().getOffset(null) / 1000;
        status = ServerStatus.DOWN;
        dontRecommend = configuration.getBoolean("Bacta/GameServer", "DontRecommended");
        onlinePlayerLimit = configuration.getInt("Bacta/GameServer", "OnlinePlayerLimit");
        onlineFreeTrialLimit = configuration.getInt("Bacta/GameServer", "OnlineFreeTrialLimit");
    }

    public ClusterStatus(Map<String, Object> clusterInfo) {
        clusterId = ((Double)clusterInfo.get("id")).intValue();
        name = (String) clusterInfo.get("name");
        connectionServerAddress = (String) clusterInfo.get("address");
        connectionServerPort = ((Double)clusterInfo.get("port")).shortValue();
        connectionServerPingPort = ((Double)clusterInfo.get("pingPort")).shortValue();
        populationOnline = ((Double)clusterInfo.get("population")).intValue();
        populationOnlineStatus = PopulationStatus.values()[((Double)clusterInfo.get("maximumPopulation")).intValue()];
        maxCharactersPerAccount = ((Double)clusterInfo.get("maximumCharacters")).intValue();
        timeZone = ((Double)clusterInfo.get("timezone")).intValue();
        status = ServerStatus.values()[((Double)clusterInfo.get("status")).intValue()];
        dontRecommend = (boolean) clusterInfo.get("dontRecommend");
        onlinePlayerLimit = ((Double)clusterInfo.get("onlinePlayerLimit")).intValue();
        onlineFreeTrialLimit = ((Double)clusterInfo.get("onlineFreeTrialLimit")).intValue();
    }

    public ClusterStatus(ByteBuffer buffer) {
        clusterId = buffer.getInt();
        name = BufferUtil.getAscii(buffer);
        connectionServerAddress = BufferUtil.getAscii(buffer);
        connectionServerPort = buffer.getShort();
        connectionServerPingPort = buffer.getShort();
        populationOnline = buffer.getInt();
        populationOnlineStatus = PopulationStatus.values()[buffer.getInt()];
        maxCharactersPerAccount = buffer.getInt();
        timeZone = buffer.getInt();
        status = ServerStatus.values()[buffer.getInt()];
        dontRecommend = BufferUtil.getBoolean(buffer);
        onlinePlayerLimit = buffer.getInt();
        onlineFreeTrialLimit = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(clusterId);
        BufferUtil.putAscii(buffer, name);
        BufferUtil.putAscii(buffer, connectionServerAddress);
        buffer.putShort(connectionServerPort);
        buffer.putShort(connectionServerPingPort);
        buffer.putInt(populationOnline);
        populationOnlineStatus.writeToBuffer(buffer);
        buffer.putInt(maxCharactersPerAccount);
        buffer.putInt(timeZone);
        status.writeToBuffer(buffer);
        BufferUtil.putBoolean(buffer, dontRecommend);
        buffer.putInt(onlinePlayerLimit);
        buffer.putInt(onlineFreeTrialLimit);
    }

    public boolean isDown() { return status == ServerStatus.DOWN; }
    public boolean isLoading() { return status == ServerStatus.LOADING; }
    public boolean isUp()  { return status == ServerStatus.UP;  }
    public boolean isLocked()  { return status == ServerStatus.LOCKED;  }
    public boolean isRestricted()  { return status == ServerStatus.RESTRICTED;  }
    public boolean isFull()  { return status == ServerStatus.FULL;  }
    public boolean isRecommended()  { return !dontRecommend;  }
}
