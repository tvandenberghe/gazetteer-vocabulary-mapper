/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.general;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thomas
 */
public abstract class KnowledgeIndexMatcher<L extends LocalEntry, R extends RemoteEntry> {

    public abstract Map<L, Set<R>> getFeatures();

    public abstract KnowledgeIndexApi getApi();

    public abstract R reconcileSingle(L location) throws MappingException;

    public void reconcile(boolean print) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        final int size = getFeatures().size();
        final String apiName = getApi().getName();
        int i = 0;
        long time = System.currentTimeMillis();
        final Printer p = print ? new Printer(KnowledgeIndexMatcher.this, true) : null;
        for (Map.Entry<L, Set<R>> entry : getFeatures().entrySet()) {
            i++;
            final int j = i;
            L localEntry = entry.getKey();
            final String name = localEntry.getName();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Searching on " + apiName + " for '" + name + "' (" + j + "/" + size + "). ");
                        reconcileSingle(localEntry);
                        if (print) {
                            p.printToFile();
                        }
                    } catch (MappingException ex) {
                        Logger.getLogger(KnowledgeIndexMatcher.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(KnowledgeIndexMatcher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            service.execute(task);
        }
        service.shutdown();
        try {
            service.awaitTermination(2L, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "An exception occured", ex);
        }

        long runTime = System.currentTimeMillis() - time;
        System.out.println("Reconciling ran for " + runTime / 1000 + " seconds.");
    }
}
