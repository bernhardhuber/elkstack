/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.elkstack.gelf1.standalone;

import java.util.Map;
import java.util.HashMap;
import biz.paluch.logging.gelf.standalone.Datenpumpe;

/**
 * A very simple Datenpumpe message-submitter.
 *
 * @author berni3
 */
public class DatenpumpeMessagesSubmitter {

    public void submitMessage(final Datenpumpe datenpumpe, final String m) {
        final Map<String, Object> message = new HashMap<String, Object>();
        message.put("message", m);
        datenpumpe.submit(message);
    }
}
