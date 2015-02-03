package com.ocdsoft.bacta.swg.cu.controller.game.server;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.SwgController;
import com.ocdsoft.bacta.soe.SwgMessageController;
import com.ocdsoft.bacta.soe.annotation.RolesAllowed;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.cu.message.game.client.ClientRandomNameRequest;
import com.ocdsoft.bacta.swg.cu.message.game.server.create.ClientRandomNameResponse;
import com.ocdsoft.bacta.swg.lang.Gender;
import com.ocdsoft.bacta.swg.lang.Race;
import com.ocdsoft.bacta.swg.name.NameService;
import com.ocdsoft.bacta.swg.shared.localization.StringId;

@SwgController(server=ServerType.GAME, handles=ClientRandomNameRequest.class)
@RolesAllowed({ConnectionRole.AUTHENTICATED})
public class ClientRandomNameRequestController implements SwgMessageController<ClientRandomNameRequest> {

    //private static final Logger logger = LoggerFactory.getLogger(ClientRandomNameRequestController.class.getSimpleName());

    private final NameService nameService;

    @Inject
    public ClientRandomNameRequestController(NameService nameService) {
        this.nameService = nameService;
    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, ClientRandomNameRequest message) throws Exception {

        Race race = parseRace(message.getCreatureTemplate());
        Gender gender = parseGender(message.getCreatureTemplate());

        String fullName = nameService.generateName(NameService.PLAYER, race, gender);

        connection.sendMessage(
                new ClientRandomNameResponse(
                        message.getCreatureTemplate(), 
                        fullName,
                        new StringId("ui", "name_approved")
                )
        );
    }

    private Race parseRace(String raceString) {
        String race = raceString.substring(raceString.lastIndexOf("/") + 1, raceString.lastIndexOf("_"));
        return Race.valueOf(race.toUpperCase());
    }

    private Gender parseGender(String raceString) {
        String gender = raceString.substring(raceString.lastIndexOf("_") + 1, raceString.indexOf(".iff"));
        return Gender.valueOf(gender.toUpperCase());
    }
}

