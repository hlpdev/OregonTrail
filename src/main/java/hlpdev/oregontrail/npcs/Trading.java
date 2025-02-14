package hlpdev.oregontrail.npcs;

import java.io.File;
import java.net.URL;
import java.util.*;

public abstract class Trading {
    public static Trade getRandomTrade() {
        List<Class<? extends Trade>> trades = new ArrayList<>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = "hlpdev/oregontrail/npcs/trades";
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource.getProtocol().equals("file")) {
                    trades.addAll(findClassesInDirectory(resource.getPath()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (trades.isEmpty()) {
            return null;
        }

        try {
            return trades.get(new Random().nextInt(trades.size())).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Class<? extends Trade>> findClassesInDirectory(String directory) {
        List<Class<? extends Trade>> trades = new ArrayList<>();

        File dir = new File(directory);
        if (!dir.exists()) {
            return trades;
        }

        for (String file : Objects.requireNonNull(dir.list())) {
            if (file.endsWith(".class")) {
                String className = "hlpdev.oregontrail.npcs.trades" + '.' + file.substring(0, file.length() - 6);
                try {
                    Class<?> clazz = Class.forName(className);
                    if (Trade.class.isAssignableFrom(clazz) && !clazz.isInterface()) {
                        trades.add((Class<? extends Trade>) clazz);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return trades;
    }
}
