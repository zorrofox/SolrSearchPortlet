package com.zhiren.portlet.config

import com.zhiren.portlet.domain.SolrServerSetting

import javax.portlet.*

class SolrServerPortlet {

    def solrSchemaService

    def title = 'Solr Server Configuration'
    def description = '''
Solr Server Configuration.
'''
    def displayName = 'Solr Server Configuration'
    def supports = ['text/html': ['view', 'help']]

    //uncomment to declare events support
    //def events = [publish: ["event-1"], process: ["event-2"]]

    //uncomment to declare public render parameter support
    //def public_render_params = ["prp-1","prp-2"]

    // Used for liferay
    // @see http://www.liferay.com/documentation/liferay-portal/6.0/development/-/ai/anatomy-of-a-portlet
    // def liferay_display_category = "category.sample"
    def liferay_portlet_control_panel_entry_category = "server"
    def liferay_portlet_control_panel_entry_weight = "9999.0"
    def liferay_portlet_instanceable = "false"
    def liferay_portlet_system = "true"
    def liferay_portlet_use_default_template = "false"

    def actionView = {
        //TODO Define action phase for 'view' portlet mode
        portletResponse.setRenderParameter("messageFlag", "T");
        println("Update Solr Server URL params: " + params)
        if (params.url != null) {
            solrSchemaService.solrServerConfig(params.url)
            portletResponse.setRenderParameter("message", "Update Success!")
            portletResponse.setRenderParameter("URL", params.url)
        } else {
            portletResponse.setRenderParameter("message", "No params, Update Fail!")
        }
    }

    def eventView = {
        //TODO Define event phase for 'view' portlet mode.
        def eventValue = portletRequest.event.value
    }

    def renderView = {
        //TODO Define render phase for 'view' portlet mode.
        //Return the map of the variables bound to the view,
        //in this case view.gsp if it exists or render.gsp if not
        println("Render params: " + params)
        def returnObj = [:]
        if (params.toolbarItem == "server") {
            if (params.messageFlag == "T") {
                returnObj.put("messageFlag", "T")
                returnObj.put("message", params.message)
                returnObj.put("solrURL", params.URL)
            } else {
                returnObj.put("messageFlag", "F")
                def attr = SolrServerSetting.findByAttributeName("URL")
                if (attr != null)
                    returnObj.put("solrURL", attr.attributeValue)
                else
                    returnObj.put("solrURL", null)
            }
        }else{

        }
        returnObj
    }

    def resourceView = {
        //TODO define resource phase for 'view' portlet mode.
        //Render HTML as response
        render {
            html {
                head()
                body {
                    "Render me!!"
                }
            }
        }
    }

    def actionEdit = {
        //TODO Define action phase for 'edit' portlet mode
    }

    def renderHelp = {
        //TODO Define render phase for 'help' portlet mode
        //Return the map of the variables bound to the view,
        //in this case help.gsp if it exists or render.gsp if not
        ['mykey': 'myvalue']
    }

    def doResource = {
        //TODO Define handling for default resource URL handling method, independent of porlet mode
        //Return the map of the variables bound to the view,
        //in this case resource.gsp
        ['mykey': 'myvalue']
    }

    //invoked by setting 'action' param in resourceURL (as an example) to 'doSomethingAjaxy'
    def doSomethingAjaxy = {
        //render JSON
        render(contentType: "text/json") {
            example(mykey: "myvalue")
        }
    }

    //invoked by setting 'action' param in eventURL (as an example) to 'handleThisEvent'
    def handleThisEvent = {
        //render thisEvent.gsp
        render(view: "thisEvent")
    }
}
