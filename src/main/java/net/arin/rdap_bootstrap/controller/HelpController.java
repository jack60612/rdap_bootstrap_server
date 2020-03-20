package net.arin.rdap_bootstrap.controller;

import net.arin.rdap_bootstrap.Constants;
import net.arin.rdap_bootstrap.json.Notice;
import net.arin.rdap_bootstrap.json.Response;
import net.arin.rdap_bootstrap.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class HelpController
{
    @Autowired
    private Statistics statistics;

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

    @RequestMapping( value = "/help", produces = { Constants.RDAP_CONTENT_TYPE })
    public Response help()
    {
        Response response = new Response( null );
        ArrayList<Notice> notices = new ArrayList<Notice>();

        // do statistics
        for ( Statistics.UrlHits stats : Statistics.UrlHits.values() )
        {
            //notices.add( makeStatsNotice( stats ) );
        }

        // totals
        Notice notice = new Notice();
        notice.setTitle( "Totals" );
        String[] description = new String[2];
        description[0] = String.format( "Hits   = %5d", statistics.getTotalHits().get() );
        description[1] = String.format( "Misses = %5d", statistics.getTotalMisses().get() );
        notice.setDescription( description );
        notices.add( notice );

        // Modified dates for various bootstrap files, done this way so that
        // Publication dates can be published as well.
        notices.add( createPublicationDateNotice( "Default",
                resourceFiles.getLastModified( ResourceFiles.BootFiles.DEFAULT.getKey() ),
                defaultBootstrap.getPublication() ) );
        notices.add( createPublicationDateNotice( "As",
                resourceFiles.getLastModified( ResourceFiles.BootFiles.AS.getKey() ),
                asBootstrap.getPublication() ) );
        notices.add( createPublicationDateNotice( "Domain",
                resourceFiles.getLastModified( ResourceFiles.BootFiles.DOMAIN.getKey() ),
                domainBootstrap.getPublication() ) );
        notices.add( createPublicationDateNotice( "Entity",
                resourceFiles.getLastModified( ResourceFiles.BootFiles.ENTITY.getKey() ),
                entityBootstrap.getPublication() ) );
        notices.add( createPublicationDateNotice( "IpV4",
                resourceFiles.getLastModified( ResourceFiles.BootFiles.V4.getKey() ),
                ipV4Bootstrap.getPublication() ) );
        notices.add( createPublicationDateNotice( "IpV6",
                resourceFiles.getLastModified( ResourceFiles.BootFiles.V6.getKey() ),
                ipV6Bootstrap.getPublication() ) );

        response.setNotices( notices );

        return response;
    }

    private Notice createPublicationDateNotice( String file, long lastModified,
                                                String publicationDate )
    {
        Notice bootFileModifiedNotice = new Notice();

        bootFileModifiedNotice
                .setTitle( String.format( "%s Bootstrap File Modified and Published Dates", file ) );
        String[] bootFileModifiedDescription = new String[2];
        // Date format as 2015-05-15T17:04:06-0500 (Y-m-d'T'H:M:Sz)
        bootFileModifiedDescription[0] = String.format( "%1$tFT%1$tT%1$tz", lastModified );
        bootFileModifiedDescription[1] = publicationDate;
        bootFileModifiedNotice.setDescription( bootFileModifiedDescription );

        return bootFileModifiedNotice;
    }
}
