/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.yarn.api.records.impl.pb;

import org.apache.hadoop.yarn.api.records.NodeAttributeKey;
import org.apache.hadoop.yarn.api.records.NodeAttributeInfo;
import org.apache.hadoop.yarn.api.records.NodeAttributeType;
import org.apache.hadoop.yarn.proto.YarnProtos.NodeAttributeKeyProto;
import org.apache.hadoop.yarn.proto.YarnProtos.NodeAttributeInfoProto;
import org.apache.hadoop.yarn.proto.YarnProtos.NodeAttributeInfoProtoOrBuilder;
import org.apache.hadoop.yarn.proto.YarnProtos.NodeAttributeTypeProto;

/**
 * Implementation for NodeAttributeInfo.
 *
 */
public class NodeAttributeInfoPBImpl extends NodeAttributeInfo {
  private NodeAttributeInfoProto proto =
      NodeAttributeInfoProto.getDefaultInstance();
  private NodeAttributeInfoProto.Builder builder = null;
  private boolean viaProto = false;

  public NodeAttributeInfoPBImpl() {
    builder = NodeAttributeInfoProto.newBuilder();
  }

  public NodeAttributeInfoPBImpl(NodeAttributeInfoProto proto) {
    this.proto = proto;
    viaProto = true;
  }

  public NodeAttributeInfoProto getProto() {
    proto = viaProto ? proto : builder.build();
    viaProto = true;
    return proto;
  }

  private void maybeInitBuilder() {
    if (viaProto || builder == null) {
      builder = NodeAttributeInfoProto.newBuilder(proto);
    }
    viaProto = false;
  }

  @Override
  public NodeAttributeKey getAttributeKey() {
    NodeAttributeInfoProtoOrBuilder p = viaProto ? proto : builder;
    if (!p.hasAttributeKey()) {
      return null;
    }
    return convertFromProtoFormat(p.getAttributeKey());
  }

  @Override
  public void setAttributeKey(NodeAttributeKey attributeKey) {
    maybeInitBuilder();
    if (attributeKey == null) {
      builder.clearAttributeKey();
      return;
    }
    builder.setAttributeKey(convertToProtoFormat(attributeKey));
  }

  @Override
  public NodeAttributeType getAttributeType() {
    NodeAttributeInfoProtoOrBuilder p = viaProto ? proto : builder;
    if (!p.hasAttributeType()) {
      return null;
    }
    return convertFromProtoFormat(p.getAttributeType());
  }

  @Override
  public void setAttributeType(NodeAttributeType attributeType) {
    maybeInitBuilder();
    if (attributeType == null) {
      builder.clearAttributeType();
      return;
    }
    builder.setAttributeType(convertToProtoFormat(attributeType));
  }

  private NodeAttributeTypeProto convertToProtoFormat(
      NodeAttributeType attributeType) {
    return NodeAttributeTypeProto.valueOf(attributeType.name());
  }

  private NodeAttributeType convertFromProtoFormat(
      NodeAttributeTypeProto containerState) {
    return NodeAttributeType.valueOf(containerState.name());
  }

  private NodeAttributeKeyPBImpl convertFromProtoFormat(
      NodeAttributeKeyProto attributeKeyProto) {
    return new NodeAttributeKeyPBImpl(attributeKeyProto);
  }

  private NodeAttributeKeyProto convertToProtoFormat(
      NodeAttributeKey attributeKey) {
    return ((NodeAttributeKeyPBImpl) attributeKey).getProto();
  }

  @Override
  public int hashCode() {
    return getAttributeKey().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (obj instanceof NodeAttributeInfo) {
      NodeAttributeInfo other = (NodeAttributeInfo) obj;
      return getAttributeKey().equals(other.getAttributeKey());
    }
    return false;
  }

  @Override
  public String toString() {
    StringBuilder strBuilder = new StringBuilder();
    NodeAttributeKey key = this.getAttributeKey();
    strBuilder.append(key.getAttributePrefix()).append("/")
        .append(key.getAttributeName()).append("(")
        .append(this.getAttributeType()).append(")");
    return strBuilder.toString();
  }
}
