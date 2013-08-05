/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2006-2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/
package org.opennms.features.vaadin.events;

import java.io.File;
import java.io.FileWriter;

import org.opennms.core.utils.ConfigFileConstants;
import org.opennms.core.xml.JaxbUtils;
import org.opennms.features.vaadin.api.Logger;
import org.opennms.netmgt.EventConstants;
import org.opennms.netmgt.config.EventConfDao;
import org.opennms.netmgt.model.events.EventBuilder;
import org.opennms.netmgt.model.events.EventProxy;
import org.opennms.netmgt.xml.eventconf.AlarmData;
import org.opennms.netmgt.xml.eventconf.Events;
import org.opennms.netmgt.xml.eventconf.Logmsg;
import org.opennms.netmgt.xml.eventconf.Mask;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class Event Panel.
 * 
 * @author <a href="mailto:agalue@opennms.org">Alejandro Galue</a> 
 */
@SuppressWarnings("serial")
public abstract class EventPanel extends Panel {

    /** The event table. */
    private final EventTable eventTable;

    /** The isNew flag. True, if the group is new. */
    private boolean isNew = false;

    /** The Events Configuration DAO. */
    private EventConfDao eventConfDao;

    /** The Events Proxy. */
    private EventProxy eventProxy;

    /** The Events File name. */
    private String fileName;

    /**
     * Instantiates a new event panel.
     *
     * @param eventConfDao the OpenNMS Events Configuration DAO
     * @param eventProxy the OpenNMS Events Proxy
     * @param fileName the MIB's file name
     * @param events the OpenNMS events object
     * @param logger the logger object
     */
    public EventPanel(final EventConfDao eventConfDao, final EventProxy eventProxy, final String fileName, final Events events, final Logger logger) {

        if (eventProxy == null)
            throw new RuntimeException("eventProxy cannot be null.");
        if (eventConfDao == null)
            throw new RuntimeException("eventConfDao cannot be null.");

        this.eventConfDao = eventConfDao;
        this.eventProxy = eventProxy;
        this.fileName = fileName;

        setCaption("Events");
        addStyleName("light");

        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addComponent(new Button("Save Events File", new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                events.setEvent(eventTable.getOnmsEvents());
                logger.info("The events have been saved.");
                processEvents(events, logger);
            }
        }));
        toolbar.addComponent(new Button("Cancel", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                logger.info("Event processing has been canceled");
                cancel();
            }
        }));

        final EventForm eventForm = new EventForm() {
            @Override
            public void saveEvent(org.opennms.netmgt.xml.eventconf.Event event) {
                if (isNew) {
                    eventTable.addEvent(event);
                    logger.info("Event " + event.getUei() + " has been created.");
                } else {
                    logger.info("Event " + event.getUei() + " has been updated.");
                }
                eventTable.refreshRowCache();
            }
            @Override
            public void deleteEvent(org.opennms.netmgt.xml.eventconf.Event event) {
                logger.info("Event " + event.getUei() + " has been removed.");
                eventTable.select(null);
                eventTable.removeItem(event.getUei());
                eventTable.refreshRowCache();
            }
        };

        eventTable = new EventTable(events) {
            @Override
            public void updateExternalSource(org.opennms.netmgt.xml.eventconf.Event event) {
                eventForm.setEventDataSource(event);
                eventForm.setVisible(true);
                eventForm.setReadOnly(true);
                setIsNew(false);
            }
        };

        final Button add = new Button("Add Event", new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                org.opennms.netmgt.xml.eventconf.Event e = new org.opennms.netmgt.xml.eventconf.Event();
                e.setUei("uei.opennms.org/newEvent");
                e.setEventLabel("New Event");
                e.setDescr("New Event Description");
                e.setLogmsg(new Logmsg());
                e.getLogmsg().setContent("New Event Log Message");
                e.getLogmsg().setDest("logndisplay");
                e.setSeverity("Indeterminate");
                e.setMask(new Mask());
                e.setAlarmData(new AlarmData());
                eventTable.updateExternalSource(e);
                eventForm.setReadOnly(false);
                setIsNew(true);
            }
        });

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.addComponent(toolbar);
        mainLayout.addComponent(eventTable);
        mainLayout.addComponent(add);
        mainLayout.addComponent(eventForm);
        mainLayout.setComponentAlignment(toolbar, Alignment.MIDDLE_RIGHT);
        mainLayout.setComponentAlignment(add, Alignment.MIDDLE_RIGHT);

        setContent(mainLayout);
    }

    /**
     * Sets the value of the ifNew flag.
     *
     * @param isNew true, if the group is new.
     */
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    /**
     * Cancel.
     */
    public abstract void cancel();

    /**
     * Success.
     */
    public abstract void success();

    /**
     * Failure.
     */
    public abstract void failure();

    /**
     * Process events.
     *
     * @param events the OpenNMS Events
     * @param logger the logger
     */
    public void processEvents(final Events events, final Logger logger) {
        final File configDir = new File(ConfigFileConstants.getHome(), "etc/events/");
        final File file = new File(configDir, fileName);
        if (file.exists()) {
            ConfirmDialog.show(getUI(),
                               "Are you sure?",
                               "Do you really want to override the existig file?\nAll current information will be lost.",
                               "Yes",
                               "No",
                               new ConfirmDialog.Listener() {
                public void onClose(ConfirmDialog dialog) {
                    if (dialog.isConfirmed()) {
                        validateFile(file, events, logger);
                    }
                }
            });
        } else {
            validateFile(file, events, logger);
        }
    }

    /**
     * Validate file.
     *
     * @param file the file
     * @param events the events
     * @param logger the logger
     */
    private void validateFile(final File file, final Events events, final Logger logger) {
        int eventCount = 0;
        for (org.opennms.netmgt.xml.eventconf.Event e : events.getEventCollection()) {
            if (eventConfDao.findByUei(e.getUei()) != null)
                eventCount++;
        }
        if (eventCount == 0) {
            saveFile(file, events, logger);
        } else {
            ConfirmDialog.show(getUI(),
                               "Are you sure?",
                               eventCount + " of the new events are already on the configuration files.\nDo you really want to override those events ?",
                               "Yes",
                               "No",
                               new ConfirmDialog.Listener() {
                public void onClose(ConfirmDialog dialog) {
                    if (dialog.isConfirmed()) {
                        saveFile(file, events, logger);
                    }
                }
            });
        }
    }

    /**
     * Save file.
     *
     * @param file the file
     * @param events the events
     * @param logger the logger
     */
    private void saveFile(final File file, final Events events, final Logger logger) {
        try {
            logger.info("Saving XML data into " + file.getAbsolutePath());
            // Save the XML of the new events
            FileWriter writer = new FileWriter(file);
            JaxbUtils.marshal(events, writer);
            writer.close();
            // Add a reference to the new file into eventconf.xml
            String fileName = "events/" + file.getName();
            if (!eventConfDao.getRootEvents().getEventFileCollection().contains(fileName)) {
                logger.info("Adding a reference to " + file.getName() + " inside eventconf.xml.");
                eventConfDao.getRootEvents().getEventFileCollection().add(0, fileName);
                eventConfDao.saveCurrent();
            }
            // Send eventsConfigChange event
            EventBuilder eb = new EventBuilder(EventConstants.EVENTSCONFIG_CHANGED_EVENT_UEI, "WebUI");
            eventProxy.send(eb.getEvent());
            logger.info("The event's configuration reload operation is being performed.");
            success();
        } catch (Exception e) {
            logger.error(e.getMessage());
            failure();
        }
    }
}
