// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.icee.myth.protobuf;

public final class BattleProtocol {
  private BattleProtocol() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public static final class StartSkillProto extends
      com.google.protobuf.GeneratedMessage {
    // Use StartSkillProto.newBuilder() to construct.
    private StartSkillProto() {}
    
    private static final StartSkillProto defaultInstance = new StartSkillProto();
    public static StartSkillProto getDefaultInstance() {
      return defaultInstance;
    }
    
    public StartSkillProto getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.icee.myth.protobuf.BattleProtocol.internal_static_myth_StartSkillProto_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.icee.myth.protobuf.BattleProtocol.internal_static_myth_StartSkillProto_fieldAccessorTable;
    }
    
    // required int32 actorId = 1;
    public static final int ACTORID_FIELD_NUMBER = 1;
    private boolean hasActorId;
    private int actorId_ = 0;
    public boolean hasActorId() { return hasActorId; }
    public int getActorId() { return actorId_; }
    
    // required int32 skillId = 2;
    public static final int SKILLID_FIELD_NUMBER = 2;
    private boolean hasSkillId;
    private int skillId_ = 0;
    public boolean hasSkillId() { return hasSkillId; }
    public int getSkillId() { return skillId_; }
    
    public final boolean isInitialized() {
      if (!hasActorId) return false;
      if (!hasSkillId) return false;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (hasActorId()) {
        output.writeInt32(1, getActorId());
      }
      if (hasSkillId()) {
        output.writeInt32(2, getSkillId());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasActorId()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, getActorId());
      }
      if (hasSkillId()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, getSkillId());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static com.icee.myth.protobuf.BattleProtocol.StartSkillProto parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.StartSkillProto parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.StartSkillProto parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.StartSkillProto parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.StartSkillProto parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.StartSkillProto parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.StartSkillProto parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input).buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.StartSkillProto parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.StartSkillProto parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.StartSkillProto parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.icee.myth.protobuf.BattleProtocol.StartSkillProto prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private com.icee.myth.protobuf.BattleProtocol.StartSkillProto result;
      
      // Construct using com.icee.myth.protobuf.BattleProtocol.StartSkillProto.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new com.icee.myth.protobuf.BattleProtocol.StartSkillProto();
        return builder;
      }
      
      protected com.icee.myth.protobuf.BattleProtocol.StartSkillProto internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new com.icee.myth.protobuf.BattleProtocol.StartSkillProto();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.icee.myth.protobuf.BattleProtocol.StartSkillProto.getDescriptor();
      }
      
      public com.icee.myth.protobuf.BattleProtocol.StartSkillProto getDefaultInstanceForType() {
        return com.icee.myth.protobuf.BattleProtocol.StartSkillProto.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public com.icee.myth.protobuf.BattleProtocol.StartSkillProto build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private com.icee.myth.protobuf.BattleProtocol.StartSkillProto buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public com.icee.myth.protobuf.BattleProtocol.StartSkillProto buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        com.icee.myth.protobuf.BattleProtocol.StartSkillProto returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.icee.myth.protobuf.BattleProtocol.StartSkillProto) {
          return mergeFrom((com.icee.myth.protobuf.BattleProtocol.StartSkillProto)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.icee.myth.protobuf.BattleProtocol.StartSkillProto other) {
        if (other == com.icee.myth.protobuf.BattleProtocol.StartSkillProto.getDefaultInstance()) return this;
        if (other.hasActorId()) {
          setActorId(other.getActorId());
        }
        if (other.hasSkillId()) {
          setSkillId(other.getSkillId());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8: {
              setActorId(input.readInt32());
              break;
            }
            case 16: {
              setSkillId(input.readInt32());
              break;
            }
          }
        }
      }
      
      
      // required int32 actorId = 1;
      public boolean hasActorId() {
        return result.hasActorId();
      }
      public int getActorId() {
        return result.getActorId();
      }
      public Builder setActorId(int value) {
        result.hasActorId = true;
        result.actorId_ = value;
        return this;
      }
      public Builder clearActorId() {
        result.hasActorId = false;
        result.actorId_ = 0;
        return this;
      }
      
      // required int32 skillId = 2;
      public boolean hasSkillId() {
        return result.hasSkillId();
      }
      public int getSkillId() {
        return result.getSkillId();
      }
      public Builder setSkillId(int value) {
        result.hasSkillId = true;
        result.skillId_ = value;
        return this;
      }
      public Builder clearSkillId() {
        result.hasSkillId = false;
        result.skillId_ = 0;
        return this;
      }
    }
    
    static {
      com.icee.myth.protobuf.BattleProtocol.getDescriptor();
    }
    
    static {
      com.icee.myth.protobuf.BattleProtocol.internalForceInit();
    }
  }
  
  public static final class BeEffectProto extends
      com.google.protobuf.GeneratedMessage {
    // Use BeEffectProto.newBuilder() to construct.
    private BeEffectProto() {}
    
    private static final BeEffectProto defaultInstance = new BeEffectProto();
    public static BeEffectProto getDefaultInstance() {
      return defaultInstance;
    }
    
    public BeEffectProto getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.icee.myth.protobuf.BattleProtocol.internal_static_myth_BeEffectProto_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.icee.myth.protobuf.BattleProtocol.internal_static_myth_BeEffectProto_fieldAccessorTable;
    }
    
    // required int32 actorId = 1;
    public static final int ACTORID_FIELD_NUMBER = 1;
    private boolean hasActorId;
    private int actorId_ = 0;
    public boolean hasActorId() { return hasActorId; }
    public int getActorId() { return actorId_; }
    
    // optional int32 casterId = 2;
    public static final int CASTERID_FIELD_NUMBER = 2;
    private boolean hasCasterId;
    private int casterId_ = 0;
    public boolean hasCasterId() { return hasCasterId; }
    public int getCasterId() { return casterId_; }
    
    // required int32 type = 3;
    public static final int TYPE_FIELD_NUMBER = 3;
    private boolean hasType;
    private int type_ = 0;
    public boolean hasType() { return hasType; }
    public int getType() { return type_; }
    
    // required int64 changeHp = 4;
    public static final int CHANGEHP_FIELD_NUMBER = 4;
    private boolean hasChangeHp;
    private long changeHp_ = 0L;
    public boolean hasChangeHp() { return hasChangeHp; }
    public long getChangeHp() { return changeHp_; }
    
    // required int64 curHp = 5;
    public static final int CURHP_FIELD_NUMBER = 5;
    private boolean hasCurHp;
    private long curHp_ = 0L;
    public boolean hasCurHp() { return hasCurHp; }
    public long getCurHp() { return curHp_; }
    
    // required int32 skillId = 6;
    public static final int SKILLID_FIELD_NUMBER = 6;
    private boolean hasSkillId;
    private int skillId_ = 0;
    public boolean hasSkillId() { return hasSkillId; }
    public int getSkillId() { return skillId_; }
    
    // optional int32 hitPoint = 7;
    public static final int HITPOINT_FIELD_NUMBER = 7;
    private boolean hasHitPoint;
    private int hitPoint_ = 0;
    public boolean hasHitPoint() { return hasHitPoint; }
    public int getHitPoint() { return hitPoint_; }
    
    // optional .myth.RewardItemProto reward = 8;
    public static final int REWARD_FIELD_NUMBER = 8;
    private boolean hasReward;
    private com.icee.myth.protobuf.ExternalCommonProtocol.RewardItemProto reward_ = com.icee.myth.protobuf.ExternalCommonProtocol.RewardItemProto.getDefaultInstance();
    public boolean hasReward() { return hasReward; }
    public com.icee.myth.protobuf.ExternalCommonProtocol.RewardItemProto getReward() { return reward_; }
    
    public final boolean isInitialized() {
      if (!hasActorId) return false;
      if (!hasType) return false;
      if (!hasChangeHp) return false;
      if (!hasCurHp) return false;
      if (!hasSkillId) return false;
      if (hasReward()) {
        if (!getReward().isInitialized()) return false;
      }
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (hasActorId()) {
        output.writeInt32(1, getActorId());
      }
      if (hasCasterId()) {
        output.writeInt32(2, getCasterId());
      }
      if (hasType()) {
        output.writeInt32(3, getType());
      }
      if (hasChangeHp()) {
        output.writeInt64(4, getChangeHp());
      }
      if (hasCurHp()) {
        output.writeInt64(5, getCurHp());
      }
      if (hasSkillId()) {
        output.writeInt32(6, getSkillId());
      }
      if (hasHitPoint()) {
        output.writeInt32(7, getHitPoint());
      }
      if (hasReward()) {
        output.writeMessage(8, getReward());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasActorId()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, getActorId());
      }
      if (hasCasterId()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, getCasterId());
      }
      if (hasType()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, getType());
      }
      if (hasChangeHp()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(4, getChangeHp());
      }
      if (hasCurHp()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(5, getCurHp());
      }
      if (hasSkillId()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(6, getSkillId());
      }
      if (hasHitPoint()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(7, getHitPoint());
      }
      if (hasReward()) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(8, getReward());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static com.icee.myth.protobuf.BattleProtocol.BeEffectProto parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.BeEffectProto parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.BeEffectProto parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.BeEffectProto parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.BeEffectProto parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.BeEffectProto parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.BeEffectProto parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input).buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.BeEffectProto parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.BeEffectProto parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.icee.myth.protobuf.BattleProtocol.BeEffectProto parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.icee.myth.protobuf.BattleProtocol.BeEffectProto prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private com.icee.myth.protobuf.BattleProtocol.BeEffectProto result;
      
      // Construct using com.icee.myth.protobuf.BattleProtocol.BeEffectProto.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new com.icee.myth.protobuf.BattleProtocol.BeEffectProto();
        return builder;
      }
      
      protected com.icee.myth.protobuf.BattleProtocol.BeEffectProto internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new com.icee.myth.protobuf.BattleProtocol.BeEffectProto();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.icee.myth.protobuf.BattleProtocol.BeEffectProto.getDescriptor();
      }
      
      public com.icee.myth.protobuf.BattleProtocol.BeEffectProto getDefaultInstanceForType() {
        return com.icee.myth.protobuf.BattleProtocol.BeEffectProto.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public com.icee.myth.protobuf.BattleProtocol.BeEffectProto build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private com.icee.myth.protobuf.BattleProtocol.BeEffectProto buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public com.icee.myth.protobuf.BattleProtocol.BeEffectProto buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        com.icee.myth.protobuf.BattleProtocol.BeEffectProto returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.icee.myth.protobuf.BattleProtocol.BeEffectProto) {
          return mergeFrom((com.icee.myth.protobuf.BattleProtocol.BeEffectProto)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.icee.myth.protobuf.BattleProtocol.BeEffectProto other) {
        if (other == com.icee.myth.protobuf.BattleProtocol.BeEffectProto.getDefaultInstance()) return this;
        if (other.hasActorId()) {
          setActorId(other.getActorId());
        }
        if (other.hasCasterId()) {
          setCasterId(other.getCasterId());
        }
        if (other.hasType()) {
          setType(other.getType());
        }
        if (other.hasChangeHp()) {
          setChangeHp(other.getChangeHp());
        }
        if (other.hasCurHp()) {
          setCurHp(other.getCurHp());
        }
        if (other.hasSkillId()) {
          setSkillId(other.getSkillId());
        }
        if (other.hasHitPoint()) {
          setHitPoint(other.getHitPoint());
        }
        if (other.hasReward()) {
          mergeReward(other.getReward());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8: {
              setActorId(input.readInt32());
              break;
            }
            case 16: {
              setCasterId(input.readInt32());
              break;
            }
            case 24: {
              setType(input.readInt32());
              break;
            }
            case 32: {
              setChangeHp(input.readInt64());
              break;
            }
            case 40: {
              setCurHp(input.readInt64());
              break;
            }
            case 48: {
              setSkillId(input.readInt32());
              break;
            }
            case 56: {
              setHitPoint(input.readInt32());
              break;
            }
            case 66: {
              com.icee.myth.protobuf.ExternalCommonProtocol.RewardItemProto.Builder subBuilder = com.icee.myth.protobuf.ExternalCommonProtocol.RewardItemProto.newBuilder();
              if (hasReward()) {
                subBuilder.mergeFrom(getReward());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setReward(subBuilder.buildPartial());
              break;
            }
          }
        }
      }
      
      
      // required int32 actorId = 1;
      public boolean hasActorId() {
        return result.hasActorId();
      }
      public int getActorId() {
        return result.getActorId();
      }
      public Builder setActorId(int value) {
        result.hasActorId = true;
        result.actorId_ = value;
        return this;
      }
      public Builder clearActorId() {
        result.hasActorId = false;
        result.actorId_ = 0;
        return this;
      }
      
      // optional int32 casterId = 2;
      public boolean hasCasterId() {
        return result.hasCasterId();
      }
      public int getCasterId() {
        return result.getCasterId();
      }
      public Builder setCasterId(int value) {
        result.hasCasterId = true;
        result.casterId_ = value;
        return this;
      }
      public Builder clearCasterId() {
        result.hasCasterId = false;
        result.casterId_ = 0;
        return this;
      }
      
      // required int32 type = 3;
      public boolean hasType() {
        return result.hasType();
      }
      public int getType() {
        return result.getType();
      }
      public Builder setType(int value) {
        result.hasType = true;
        result.type_ = value;
        return this;
      }
      public Builder clearType() {
        result.hasType = false;
        result.type_ = 0;
        return this;
      }
      
      // required int64 changeHp = 4;
      public boolean hasChangeHp() {
        return result.hasChangeHp();
      }
      public long getChangeHp() {
        return result.getChangeHp();
      }
      public Builder setChangeHp(long value) {
        result.hasChangeHp = true;
        result.changeHp_ = value;
        return this;
      }
      public Builder clearChangeHp() {
        result.hasChangeHp = false;
        result.changeHp_ = 0L;
        return this;
      }
      
      // required int64 curHp = 5;
      public boolean hasCurHp() {
        return result.hasCurHp();
      }
      public long getCurHp() {
        return result.getCurHp();
      }
      public Builder setCurHp(long value) {
        result.hasCurHp = true;
        result.curHp_ = value;
        return this;
      }
      public Builder clearCurHp() {
        result.hasCurHp = false;
        result.curHp_ = 0L;
        return this;
      }
      
      // required int32 skillId = 6;
      public boolean hasSkillId() {
        return result.hasSkillId();
      }
      public int getSkillId() {
        return result.getSkillId();
      }
      public Builder setSkillId(int value) {
        result.hasSkillId = true;
        result.skillId_ = value;
        return this;
      }
      public Builder clearSkillId() {
        result.hasSkillId = false;
        result.skillId_ = 0;
        return this;
      }
      
      // optional int32 hitPoint = 7;
      public boolean hasHitPoint() {
        return result.hasHitPoint();
      }
      public int getHitPoint() {
        return result.getHitPoint();
      }
      public Builder setHitPoint(int value) {
        result.hasHitPoint = true;
        result.hitPoint_ = value;
        return this;
      }
      public Builder clearHitPoint() {
        result.hasHitPoint = false;
        result.hitPoint_ = 0;
        return this;
      }
      
      // optional .myth.RewardItemProto reward = 8;
      public boolean hasReward() {
        return result.hasReward();
      }
      public com.icee.myth.protobuf.ExternalCommonProtocol.RewardItemProto getReward() {
        return result.getReward();
      }
      public Builder setReward(com.icee.myth.protobuf.ExternalCommonProtocol.RewardItemProto value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasReward = true;
        result.reward_ = value;
        return this;
      }
      public Builder setReward(com.icee.myth.protobuf.ExternalCommonProtocol.RewardItemProto.Builder builderForValue) {
        result.hasReward = true;
        result.reward_ = builderForValue.build();
        return this;
      }
      public Builder mergeReward(com.icee.myth.protobuf.ExternalCommonProtocol.RewardItemProto value) {
        if (result.hasReward() &&
            result.reward_ != com.icee.myth.protobuf.ExternalCommonProtocol.RewardItemProto.getDefaultInstance()) {
          result.reward_ =
            com.icee.myth.protobuf.ExternalCommonProtocol.RewardItemProto.newBuilder(result.reward_).mergeFrom(value).buildPartial();
        } else {
          result.reward_ = value;
        }
        result.hasReward = true;
        return this;
      }
      public Builder clearReward() {
        result.hasReward = false;
        result.reward_ = com.icee.myth.protobuf.ExternalCommonProtocol.RewardItemProto.getDefaultInstance();
        return this;
      }
    }
    
    static {
      com.icee.myth.protobuf.BattleProtocol.getDescriptor();
    }
    
    static {
      com.icee.myth.protobuf.BattleProtocol.internalForceInit();
    }
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_myth_StartSkillProto_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_myth_StartSkillProto_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_myth_BeEffectProto_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_myth_BeEffectProto_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\036src/proto/BattleProtocol.proto\022\004myth\032&" +
      "src/proto/ExternalCommonProtocol.proto\"3" +
      "\n\017StartSkillProto\022\017\n\007actorId\030\001 \002(\005\022\017\n\007sk" +
      "illId\030\002 \002(\005\"\253\001\n\rBeEffectProto\022\017\n\007actorId" +
      "\030\001 \002(\005\022\020\n\010casterId\030\002 \001(\005\022\014\n\004type\030\003 \002(\005\022\020" +
      "\n\010changeHp\030\004 \002(\003\022\r\n\005curHp\030\005 \002(\003\022\017\n\007skill" +
      "Id\030\006 \002(\005\022\020\n\010hitPoint\030\007 \001(\005\022%\n\006reward\030\010 \001" +
      "(\0132\025.myth.RewardItemProtoB\032\n\026com.icee.my" +
      "th.protobufH\001"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_myth_StartSkillProto_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_myth_StartSkillProto_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_myth_StartSkillProto_descriptor,
              new java.lang.String[] { "ActorId", "SkillId", },
              com.icee.myth.protobuf.BattleProtocol.StartSkillProto.class,
              com.icee.myth.protobuf.BattleProtocol.StartSkillProto.Builder.class);
          internal_static_myth_BeEffectProto_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_myth_BeEffectProto_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_myth_BeEffectProto_descriptor,
              new java.lang.String[] { "ActorId", "CasterId", "Type", "ChangeHp", "CurHp", "SkillId", "HitPoint", "Reward", },
              com.icee.myth.protobuf.BattleProtocol.BeEffectProto.class,
              com.icee.myth.protobuf.BattleProtocol.BeEffectProto.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.icee.myth.protobuf.ExternalCommonProtocol.getDescriptor(),
        }, assigner);
  }
  
  public static void internalForceInit() {}
}