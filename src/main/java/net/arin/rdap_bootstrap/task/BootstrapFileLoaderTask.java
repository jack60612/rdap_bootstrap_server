package net.arin.rdap_bootstrap.task;

import lombok.extern.slf4j.Slf4j;
import net.arin.rdap_bootstrap.service.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class BootstrapFileLoaderTask
{
    @Autowired
    private ResourceFiles resourceFiles;

    @Autowired
    private DefaultBootstrap defaultBootstrap;

    @Autowired
    private AsBootstrap asBootstrap;

    @Autowired
    private DomainBootstrap domainBootstrap;

    @Autowired
    private EntityBootstrap entityBootstrap;

    @Autowired
    private IpV4Bootstrap ipV4Bootstrap;

    @Autowired
    private IpV6Bootstrap ipV6Bootstrap;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info( "Refreshing resource files." );

        try
        {
            resourceFiles = new ResourceFiles();
            asBootstrap.loadData(resourceFiles);
            ipV4Bootstrap.loadData(resourceFiles);
            ipV6Bootstrap.loadData(resourceFiles);
            domainBootstrap.loadData(resourceFiles);
            entityBootstrap.loadData(resourceFiles);
            defaultBootstrap.loadData(resourceFiles);
        }
        catch( Exception ex )
        {
            log.error( "Unable to reload resource files", ex );
        }

    }
}
