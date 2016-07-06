package com.faeddalberto.nbastats.model.datastaxrepo;

import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.datastaxaccessor.PlayerCareerByNameAccessor;
import com.faeddalberto.nbastats.model.domain.PlayerCareerByName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerCareerByNameRepository {

    private MappingManager mappingManager;

    @Autowired
    private CassandraConfig cassandraConfig;

    public List<PlayerCareerByName> getPlayerCareerByName(String name) throws Exception {

        mappingManager = cassandraConfig.getMappingManager();

        PlayerCareerByNameAccessor playerCareerByNameAccessor = mappingManager.createAccessor(PlayerCareerByNameAccessor.class);

        Result<PlayerCareerByName> playerCareerByName = playerCareerByNameAccessor.getPlayerCareerByName(name);

        return playerCareerByName.all();
    }

    public List<PlayerCareerByName> getPlayerInfoByNameAndYear(String name, int year) throws Exception {

        mappingManager = cassandraConfig.getMappingManager();

        PlayerCareerByNameAccessor playerCareerByNameAccessor = mappingManager.createAccessor(PlayerCareerByNameAccessor.class);

        Result<PlayerCareerByName> playerCareerByName = playerCareerByNameAccessor.getPlayerInfoByNameAndYear(name, year);

        return playerCareerByName.all();
    }

    public void deletePlayerCareer(String name) throws Exception {

        mappingManager = cassandraConfig.getMappingManager();

        PlayerCareerByNameAccessor playerCareerByNameAccessor = mappingManager.createAccessor(PlayerCareerByNameAccessor.class);

        playerCareerByNameAccessor.deletePlayerCareer(name);
    }
}
