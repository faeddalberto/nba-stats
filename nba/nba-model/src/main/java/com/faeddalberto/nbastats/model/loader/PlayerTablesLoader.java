package com.faeddalberto.nbastats.model.loader;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.MappingManager;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.datastaxaccessor.PlayerAccessor;
import com.faeddalberto.nbastats.model.datastaxaccessor.PlayerCareerByNameAccessor;
import com.faeddalberto.nbastats.model.domain.Player;
import com.faeddalberto.nbastats.model.domain.PlayerCareerByName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerTablesLoader {

    @Autowired
    private CassandraConfig cassandraConfig;

    public void insertPlayerBatch(Player player, List<PlayerCareerByName> playerCareerByName) throws Exception {
        BatchStatement batchStatement = new BatchStatement();
        Session session = cassandraConfig.session().getObject();
        MappingManager mappingManager = new MappingManager(session);

        PlayerAccessor playerAccessor = mappingManager.createAccessor(PlayerAccessor.class);
        PlayerCareerByNameAccessor playerCareerByNameAccessor = mappingManager.createAccessor(PlayerCareerByNameAccessor.class);

        Statement playerStatement = playerAccessor.insertPlayer(player.getPlayerId(), player.getDateOfBirth(), player.getName(), player.getCountry(), player.getDraftedInfo());
        batchStatement.add(playerStatement);
        for (PlayerCareerByName playerCareerStep : playerCareerByName) {
            Statement careerStepStmt = playerCareerByNameAccessor.insertPlayerCareer(playerCareerStep.getName(), playerCareerStep.getYear(), playerCareerStep.getTeam(), playerCareerStep.getPlayerId(), playerCareerStep.getCountry(), playerCareerStep.getDateOfBirth(), playerCareerStep.getDraftedInfo(), playerCareerStep.getRole());
            batchStatement.add(careerStepStmt);
        }

        session.execute(batchStatement);
    }
}
