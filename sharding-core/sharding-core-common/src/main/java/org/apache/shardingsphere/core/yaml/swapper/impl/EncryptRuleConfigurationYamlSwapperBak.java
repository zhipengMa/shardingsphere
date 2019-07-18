/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.core.yaml.swapper.impl;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import org.apache.shardingsphere.api.config.encrypt.EncryptRuleConfiguration;
import org.apache.shardingsphere.api.config.encrypt.EncryptTableRuleConfiguration;
import org.apache.shardingsphere.api.config.encrypt.EncryptorRuleConfiguration;
import org.apache.shardingsphere.core.yaml.config.encrypt.YamlEncryptRuleConfigurationBak;
import org.apache.shardingsphere.core.yaml.config.encrypt.YamlEncryptTableRuleConfiguration;
import org.apache.shardingsphere.core.yaml.config.encrypt.YamlEncryptorRuleConfigurationBak;
import org.apache.shardingsphere.core.yaml.swapper.YamlSwapper;

/**
 * Encrypt rule configuration yaml swapper.
 *
 * @author panjuan
 */
public final class EncryptRuleConfigurationYamlSwapperBak implements YamlSwapper<YamlEncryptRuleConfigurationBak, EncryptRuleConfiguration> {
    
    private final EncryptorRuleConfigurationYamlSwapperBak encryptorRuleConfigurationYamlSwapper = new EncryptorRuleConfigurationYamlSwapperBak();
    
    private final EncryptTableRuleConfigurationYamlSwapper encryptTableRuleConfigurationYamlSwapper = new EncryptTableRuleConfigurationYamlSwapper();
    
    @Override
    public YamlEncryptRuleConfigurationBak swap(final EncryptRuleConfiguration data) {
        YamlEncryptRuleConfigurationBak result = new YamlEncryptRuleConfigurationBak();
        result.getEncryptors().putAll(Maps.transformValues(data.getEncryptors(), new Function<EncryptorRuleConfiguration, YamlEncryptorRuleConfigurationBak>() {
            
            @Override
            public YamlEncryptorRuleConfigurationBak apply(final EncryptorRuleConfiguration input) {
                return encryptorRuleConfigurationYamlSwapper.swap(input);
            }
        }));
        result.getTables().putAll(Maps.transformValues(data.getTables(), new Function<EncryptTableRuleConfiguration, YamlEncryptTableRuleConfiguration>() {
            
            @Override
            public YamlEncryptTableRuleConfiguration apply(final EncryptTableRuleConfiguration input) {
                return encryptTableRuleConfigurationYamlSwapper.swap(input);
            }
        }));
        return result;
    }
    
    @Override
    public EncryptRuleConfiguration swap(final YamlEncryptRuleConfigurationBak yamlConfiguration) {
        EncryptRuleConfiguration result = new EncryptRuleConfiguration();
        result.getEncryptors().putAll(Maps.transformValues(yamlConfiguration.getEncryptors(), new Function<YamlEncryptorRuleConfigurationBak, EncryptorRuleConfiguration>() {
        
            @Override
            public EncryptorRuleConfiguration apply(final YamlEncryptorRuleConfigurationBak input) {
                return encryptorRuleConfigurationYamlSwapper.swap(input);
            }
        }));
        result.getTables().putAll(Maps.transformValues(yamlConfiguration.getTables(), new Function<YamlEncryptTableRuleConfiguration, EncryptTableRuleConfiguration>() {
        
            @Override
            public EncryptTableRuleConfiguration apply(final YamlEncryptTableRuleConfiguration input) {
                return encryptTableRuleConfigurationYamlSwapper.swap(input);
            }
        }));
        return result;
    }
}
