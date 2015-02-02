package com.ocdsoft.bacta.swg.cu.message.game.client;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

/**
 struct __cppobj ClientCreateCharacter : GameNetworkMessage
 {
     Archive::AutoVariable<std::string > m_appearanceData;
     Archive::AutoVariable<UnicodeString > m_characterName;
     Archive::AutoVariable<std::string > m_templateName;
     Archive::AutoVariable<std::string > m_startingLocation;
     Archive::AutoVariable<std::string > m_hairTemplateName;
     Archive::AutoVariable<std::string > m_hairAppearanceData;
     Archive::AutoVariable<std::string > m_profession;
     Archive::AutoVariable<bool> m_jedi;
     Archive::AutoVariable<float> m_scaleFactor;
     Archive::AutoVariable<UnicodeString > m_biography;
     Archive::AutoVariable<bool> m_useNewbieTutorial;
     Archive::AutoVariable<std::string > m_skillTemplate;  //NGE Only
     Archive::AutoVariable<std::string > m_workingSkill;  // NGE Only
 }; 
 */
public class ClientCreateCharacter extends GameNetworkMessage {

    private static final short priority = 0x9;
    private static final int messageType = SOECRC32.hashCode(ClientCreateCharacter.class.getSimpleName());

    @Getter
    private final String appearanceData;
    
    @Getter
    private final String characterName;
    
    @Getter
    private final String templateName;
    
    @Getter
    private final String startingLocation;
    
    @Getter
    @Setter
    private String hairTemplateName;
    
    @Getter
    private final String hairAppearanceData;
    
    @Getter
    @Setter
    private String profession;
    
    @Getter
    private final boolean jedi;
    
    @Getter
    private final float scaleFactor;
    
    @Getter
    private final String biography;
    
    @Getter
    private final boolean useNewbieTutorial;


    public ClientCreateCharacter(final String appearanceData,
                                 final String characterName,
                                 final String templateName,
                                 final String startingLocation,
                                 final String hairTemplateName,
                                 final String hairAppearanceData,
                                 final String profession,
                                 final boolean jedi,
                                 final float scaleFactor,
                                 final String biography,
                                 final boolean useNewbieTutorial) {
        super(priority, messageType);

        this.appearanceData = appearanceData;
        this.characterName = characterName;
        this.templateName = templateName;
        this.startingLocation = startingLocation;
        this.hairTemplateName = hairTemplateName;
        this.hairAppearanceData = hairAppearanceData;
        this.profession = profession;
        this.jedi = jedi;
        this.scaleFactor = scaleFactor;
        this.biography = biography;
        this.useNewbieTutorial = useNewbieTutorial;
    }

    public ClientCreateCharacter(ByteBuffer buffer) {
        super(priority, messageType);
        
        this.appearanceData = BufferUtil.getAscii(buffer);
        this.characterName = BufferUtil.getUnicode(buffer);
        this.templateName = BufferUtil.getAscii(buffer);
        this.startingLocation = BufferUtil.getAscii(buffer);
        this.hairTemplateName = BufferUtil.getAscii(buffer);
        this.hairAppearanceData = BufferUtil.getAscii(buffer);
        this.profession = BufferUtil.getAscii(buffer);
        this.jedi = BufferUtil.getBoolean(buffer);
        this.scaleFactor = buffer.getFloat();
        this.biography = BufferUtil.getUnicode(buffer);
        this.useNewbieTutorial = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

        BufferUtil.putAscii(buffer, appearanceData);
        BufferUtil.putUnicode(buffer, characterName);
        BufferUtil.putAscii(buffer, templateName);
        BufferUtil.putAscii(buffer, startingLocation);
        BufferUtil.putAscii(buffer, hairTemplateName);
        BufferUtil.putAscii(buffer, hairAppearanceData);
        BufferUtil.putAscii(buffer, profession);
        BufferUtil.putBoolean(buffer, jedi);
        buffer.putFloat(scaleFactor);
        BufferUtil.putUnicode(buffer, biography);
        BufferUtil.putBoolean(buffer, useNewbieTutorial);
    }
    /**
         00 09 00 03 0C 00 74 30 7F B9 72 00 02 23 17 C3
    BF 01 18 C2 9D 1C 4C 1B C3 BF 01 05 C2 91 1A 72
    19 C3 BF 01 0D C3 BF 01 09 5F 12 C2 A1 13 C3 BF
    01 20 C3 BF 01 10 C3 AC 21 13 0F C3 BF 01 14 0A
    11 C3 95 0E C3 BF 01 03 C3 BF 01 0B C3 BF 01 0C
    C3 94 06 7C 08 C3 BF 01 15 C2 A4 16 C3 BF 01 04
    C3 8C 07 C3 BF 01 0A C3 B4 23 08 25 04 24 C3 BF
    01 01 02 1D C3 BF 01 1F 11 1E 05 C3 BF 03 04 00
    00 00 4B 00 79 00 6C 00 65 00 25 00 6F 62 6A 65
    63 74 2F 63 72 65 61 74 75 72 65 2F 70 6C 61 79
    65 72 2F 68 75 6D 61 6E 5F 6D 61 6C 65 2E 69 66
    66 07 00 62 65 73 74 69 6E 65 32 00 6F 62 6A 65
    63 74 2F 74 61 6E 67 69 62 6C 65 2F 68 61 69 72
    2F 68 75 6D 61 6E 2F 68 61 69 72 5F 68 75 6D 61
    6E 5F 6D 61 6C 65 5F 73 32 38 2E 69 66 66 07 00
    02 01 02 02 C3 BF 03 10 00 63 72 61 66 74 69 6E
    67 5F 61 72 74 69 73 61 6E 00 F4 BC 81 3F 00 00
    00 00 00 

     */
}
