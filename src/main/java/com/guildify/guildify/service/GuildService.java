package com.guildify.guildify.service;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.GuildEntity;
import com.guildify.guildify.model.UserEntity;
import com.guildify.guildify.model.dto.GameCharResponse;
import com.guildify.guildify.model.dto.GuildRequest;
import com.guildify.guildify.model.dto.GuildResponse;
import com.guildify.guildify.repository.GameCharRepository;
import com.guildify.guildify.repository.GameRepository;
import com.guildify.guildify.repository.GuildRepository;
import com.guildify.guildify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.guildify.guildify.utility.StaticMethods.isNumeric;

@Service
public class GuildService {

    @Autowired
    private GuildRepository guildRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;


    public List<GuildResponse> getAllGuilds(String jwt){
        List<GuildResponse> allGuildResponse = new ArrayList<>();
        for (GuildEntity x: guildRepository.findAll()){
            allGuildResponse.add(guildEntityToResponseMapper(jwt,x));
        }
        return allGuildResponse;
    }
    public GuildResponse getGuildById(String jwt, int guildId){
        if(guildRepository.findGuildEntityByGuildId(guildId) == null){
            throw new IllegalArgumentException("There is no such ID. GuildID = " + guildId);
        }
        return guildEntityToResponseMapper(jwt,guildRepository.findGuildEntityByGuildId(guildId));
    }

    public GuildResponse addNewGuild(String jwt, GuildRequest guildRequest) {
        if (guildRequest.getGuildName().length() < 1 || isNumeric(guildRequest.getGuildName())) {
            throw new IllegalArgumentException("The guild name cannot be only numeric or lesser than 1 characters");
        }
        if(guildRepository.findGuildEntityByGuildName(guildRequest.getGuildName())!=null){
            throw new IllegalArgumentException("Please create a guild with a different name.");
        }
        if(userRepository.findUserEntityByDisplayName(guildRequest.getGuildLeaderUserDisplayName())==null){
            throw new IllegalArgumentException("A guild must have a leader.");
        }
        if(gameRepository.findGameEntityByGameName(guildRequest.getGameName())==null){
            throw new IllegalArgumentException("A guild must be related to a game.");
        }
        // DTO to Entity mapper
        GuildEntity guildEntity = GuildEntity.builder()
                .guildName(guildRequest.getGuildName())
                .guildLeaderUserEntity(userRepository.findUserEntityByDisplayName(guildRequest.getGuildLeaderUserDisplayName()))
                .gameEntity(gameRepository.findGameEntityByGameName(guildRequest.getGameName()))
                .gameCharEntityList(null) //A new guild cannot have chars in the beginning.
                .build();
        guildEntity.setTimestamp(LocalDateTime.now());
        guildEntity.setCreatedBy(jwtUserEntityExtractor(jwt).getDisplayName());
        guildEntity = guildRepository.save(guildEntity);
        return guildEntityToResponseMapper(jwt,guildEntity);
    }
    public GuildResponse updateGuild(String jwt, GuildRequest guildRequest, int guildId) {
        if(guildRepository.findGuildEntityByGuildId(guildId)==null){
            return null;
        }
        GuildEntity guild = guildRepository.findGuildEntityByGuildId(guildId);
        guild.setGuildName(guildRequest.getGuildName());
        guild.setGuildLeaderUserEntity(userRepository.findUserEntityByDisplayName(guildRequest.getGuildLeaderUserDisplayName()));
        guild.setGameEntity(gameRepository.findGameEntityByGameName(guildRequest.getGuildName()));
        guild.setCreatedBy(jwtUserEntityExtractor(jwt).getUsername());
        guild.setTimestamp(LocalDateTime.now());
        guild = guildRepository.save(guild);
        return guildEntityToResponseMapper(jwt,guild);
    }

    public String deleteAllGuilds() {
        guildRepository.deleteAll();
        if(guildRepository.count()==0){
            return "All guilds have been removed.";
        } else{
            return "Something went wrong. Please check DB.";
        }
    }

    public String deleteExistingGuild(String jwt, int guildId) {
        if(guildRepository.findGuildEntityByGuildId(guildId)==null){
            throw new IllegalArgumentException("No guild exists with such Guild ID: " + guildId);
        }
        if(jwtUserEntityExtractor(jwt) != guildRepository.findGuildEntityByGuildId(guildId).getGuildLeaderUserEntity()){
            throw new IllegalArgumentException("Only Guild Leader can delete a guild.");
        }
        guildRepository.delete(guildRepository.findGuildEntityByGuildId(guildId));
        return "Guild Deleted Successfully. Guild ID: " + guildId;
    }
    public String deleteExistingGuildAsAdmin(int guildId) {
        if(guildRepository.findGuildEntityByGuildId(guildId)==null){
            throw new IllegalArgumentException("No guild exists with such Guild ID: " + guildId);
        }
        guildRepository.delete(guildRepository.findGuildEntityByGuildId(guildId));
        return "Guild Deleted Successfully. Guild ID: " + guildId;
    }

    public List<GameCharResponse> getSameGuildGameChars(String jwt, int guildId) {
        if(guildRepository.findGuildEntityByGuildId(guildId) == null){
            throw new IllegalArgumentException("There is no such ID. GuildID = " + guildId);
        }
        List<GameCharResponse> charsOfGuildAsResponse = new ArrayList<>();
        for(GameCharEntity x: guildRepository.findGuildEntityByGuildId(guildId).getGameCharEntityList()){
            GameCharResponse gameCharResponse = GameCharResponse.builder()
                    .charId(x.getCharId())
                    .charName(x.getCharName())
                    .charLevel(x.getCharLevel())
                    .charOwner(x.getUserEntity().getDisplayName())
                    .charGameName(x.getGameEntity().getGameName())
                    .charGuildName(x.getGuildEntity().getGuildName())
                    .build();
            gameCharResponse.setCreatedAt(LocalDateTime.now());
            gameCharResponse.setCreatedBy(jwtUserEntityExtractor(jwt).getDisplayName());
            charsOfGuildAsResponse.add(gameCharResponse);
        }
        return charsOfGuildAsResponse;
    }

    public GuildResponse guildEntityToResponseMapper(String jwt, GuildEntity guildEntity){
        GuildResponse guildResponse = GuildResponse.builder()
                .guildId(guildEntity.getGuildId())
                .guildName(guildEntity.getGuildName())
                .guildLeaderUserDisplayName(null)
                .gameName(null)
                .gameCharResponseList(null)
                .build();
        guildResponse.setCreatedAt(LocalDateTime.now());
        guildEntity.setCreatedBy(jwtUserEntityExtractor(jwt).getDisplayName());
        if(guildEntity.getGuildLeaderUserEntity()!=null){
            guildResponse.setGuildLeaderUserDisplayName(guildEntity.getGuildLeaderUserEntity().getDisplayName());
        }
        if(guildEntity.getGameEntity()!=null){
            guildResponse.setGameName(guildEntity.getGameEntity().getGameName());
        }
        if(guildEntity.getGameCharEntityList()!=null){
            List<GameCharResponse> gameCharResponseList = new ArrayList<>();
            for(GameCharEntity x:  guildEntity.getGameCharEntityList()){
                GameCharResponse gameCharResponse = GameCharResponse.builder()
                        .charId(x.getCharId())
                        .charName(x.getCharName())
                        .charLevel(x.getCharLevel())
                        .charOwner(x.getUserEntity().getDisplayName())
                        .charGameName(x.getGameEntity().getGameName())
                        .charGuildName(x.getGuildEntity().getGuildName())
                        .build();
                gameCharResponse.setCreatedBy(jwtUserEntityExtractor(jwt).getDisplayName());
                gameCharResponse.setCreatedAt(LocalDateTime.now());
                gameCharResponseList.add(gameCharResponse);
            }
            guildResponse.setGameCharResponseList(gameCharResponseList);
        }
        return guildResponse;
    }

    public UserEntity jwtUserEntityExtractor(String jwt){
        Map<String, Object> claims = tokenService.decodeJwt(jwt).getClaims();
        return userRepository.findUserEntityByUsername((String)claims.get("sub"));
    }
}
