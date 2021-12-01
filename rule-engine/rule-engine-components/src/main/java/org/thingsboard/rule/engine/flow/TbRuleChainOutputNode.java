/**
 * Copyright © 2016-2021 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.rule.engine.flow;

import lombok.extern.slf4j.Slf4j;
import org.thingsboard.rule.engine.api.RuleNode;
import org.thingsboard.rule.engine.api.TbContext;
import org.thingsboard.rule.engine.api.TbNode;
import org.thingsboard.rule.engine.api.TbNodeConfiguration;
import org.thingsboard.rule.engine.api.TbNodeException;
import org.thingsboard.rule.engine.api.TbRelationTypes;
import org.thingsboard.rule.engine.api.util.TbNodeUtils;
import org.thingsboard.server.common.data.plugin.ComponentType;
import org.thingsboard.server.common.msg.TbMsg;

@Slf4j
@RuleNode(
        type = ComponentType.ACTION,
        name = "rule chain output",
        configClazz = TbRuleChainOutputNodeConfiguration.class,
        nodeDescription = "transfers the message to the caller rule chain",
        nodeDetails = "Produces output of the rule chain processing. " +
                "The output is forwarded to the caller rule chain, as an output of the corresponding \"input\" rule node. " +
                "The rule node configuration contains the \"label\" parameter. " +
                "This parameter corresponds to the relation type of the output message, and it is used to forward messages to other rule nodes in the caller rule chain. ",
        outEnabled = false
)
public class TbRuleChainOutputNode implements TbNode {

    private TbRuleChainOutputNodeConfiguration config;

    @Override
    public void init(TbContext ctx, TbNodeConfiguration configuration) throws TbNodeException {
        this.config = TbNodeUtils.convert(configuration, TbRuleChainOutputNodeConfiguration.class);
    }

    @Override
    public void onMsg(TbContext ctx, TbMsg msg) {
        ctx.output(msg, config.getLabel());
    }

    @Override
    public void destroy() {
    }
}
