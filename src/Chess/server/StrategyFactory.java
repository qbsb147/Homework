package Chess.server;

import Chess.server.strategy.Strategy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StrategyFactory {

    private final Map<String, Strategy> strategyMap;

    public StrategyFactory(List<Strategy> strategies){
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(
                        Strategy::getKey,
                        s-> s
                ));
    }

    public Strategy getStrategy(String key){
        Strategy strategy = strategyMap.get(key);

        if(strategy == null){
            throw new IllegalArgumentException("Unknown strategy: " + key);
        }

        return strategy;
    }
}
