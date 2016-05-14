package com.faeddalberto.nbastats.model.loader;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.MappingManager;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.datastaxaccessor.PlayerStatsByGameAccessor;
import com.faeddalberto.nbastats.model.datastaxaccessor.PlayerStatsByOpponentAccessor;
import com.faeddalberto.nbastats.model.datastaxaccessor.StatsBySeasonAccessor;
import com.faeddalberto.nbastats.model.domain.PlayerStatsByGame;
import com.faeddalberto.nbastats.model.domain.PlayerStatsByOpponent;
import com.faeddalberto.nbastats.model.domain.StatsBySeason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatsTablesLoader {

    @Autowired
    private CassandraConfig cassandraConfig;

    public void insertStatsBatch(PlayerStatsByGame playerStatsByGame, PlayerStatsByOpponent playerStatsByOpponent, StatsBySeason statsBySeason) throws Exception {
        BatchStatement batchStatement = new BatchStatement();
        Session session = cassandraConfig.session().getObject();
        MappingManager mappingManager = new MappingManager(session);

        PlayerStatsByGameAccessor playerStatsByGameAccessor = mappingManager.createAccessor(PlayerStatsByGameAccessor.class);
        PlayerStatsByOpponentAccessor playerStatsByOpponentAccessor = mappingManager.createAccessor(PlayerStatsByOpponentAccessor.class);
        StatsBySeasonAccessor statsBySeasonAccessor = mappingManager.createAccessor(StatsBySeasonAccessor.class);

        Statement playerStatsByGameStmt = playerStatsByGameAccessor.insertPlayerStatsByGame(playerStatsByGame.getGameId(), playerStatsByGame.getPlayerId(), playerStatsByGame.getDate(), playerStatsByGame.getSeason(), playerStatsByGame.getPlayerTeam(), playerStatsByGame.getOpponentTeam(), playerStatsByGame.getMinsPlayed(), playerStatsByGame.getPlayerName(), playerStatsByGame.getFieldGoals(), playerStatsByGame.getThreePoints(), playerStatsByGame.getFreeThrows(), playerStatsByGame.getOffensiveRebounds(), playerStatsByGame.getDefensiveRebounds(), playerStatsByGame.getTotalRebounds(), playerStatsByGame.getAssists(), playerStatsByGame.getSteals(), playerStatsByGame.getBlocks(), playerStatsByGame.getTurnovers(), playerStatsByGame.getPersonalFauls(), playerStatsByGame.getPlusMinus(), playerStatsByGame.getPoints());
        Statement playerStatsByOpponentStmt = playerStatsByOpponentAccessor.insertplayerStatsByOpponent(playerStatsByOpponent.getOpponentTeam(), playerStatsByOpponent.getPlayerName(), playerStatsByOpponent.getPlayerTeam(), playerStatsByOpponent.getSeason(), playerStatsByOpponent.getDate(), playerStatsByOpponent.getGameId(), playerStatsByOpponent.getPlayerId(), playerStatsByOpponent.getMinsPlayed(), playerStatsByOpponent.getFieldGoals(), playerStatsByOpponent.getThreePoints(), playerStatsByOpponent.getFreeThrows(), playerStatsByOpponent.getOffensiveRebounds(), playerStatsByOpponent.getDefensiveRebounds(), playerStatsByOpponent.getTotalRebounds(), playerStatsByOpponent.getAssists(), playerStatsByOpponent.getSteals(), playerStatsByOpponent.getBlocks(), playerStatsByOpponent.getTurnovers(), playerStatsByOpponent.getPersonalFauls(), playerStatsByOpponent.getPlusMinus(), playerStatsByOpponent.getPoints());
        Statement statsBySeasonStmt = statsBySeasonAccessor.insertStatsBySeason(statsBySeason.getSeason(), statsBySeason.getMonth(),statsBySeason.getPlayerTeam(), statsBySeason.getPlayerName(), statsBySeason.getOpponentTeam(), statsBySeason.getGameId(), statsBySeason.getPlayerId(), statsBySeason.getDate(), statsBySeason.getMinsPlayed(), statsBySeason.getFieldGoals(), statsBySeason.getThreePoints(), statsBySeason.getFreeThrows(), statsBySeason.getOffensiveRebounds(), statsBySeason.getDefensiveRebounds(), statsBySeason.getTotalRebounds(), statsBySeason.getAssists(), statsBySeason.getSteals(), statsBySeason.getBlocks(), statsBySeason.getTurnovers(), statsBySeason.getPersonalFauls(), statsBySeason.getPlusMinus(), statsBySeason.getPoints());

        batchStatement.add(playerStatsByGameStmt);
        batchStatement.add(playerStatsByOpponentStmt);
        batchStatement.add(statsBySeasonStmt);

        session.execute(batchStatement);
    }
}
