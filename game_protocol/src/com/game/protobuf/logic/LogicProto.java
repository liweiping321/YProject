// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/Logic.proto

package com.game.protobuf.logic;

public final class LogicProto {
  private LogicProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface PBLoginReqOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
    
    // required string account = 1;
    boolean hasAccount();
    String getAccount();
    
    // required string playerName = 2;
    boolean hasPlayerName();
    String getPlayerName();
  }
  public static final class PBLoginReq extends
      com.google.protobuf.GeneratedMessage
      implements PBLoginReqOrBuilder {
    // Use PBLoginReq.newBuilder() to construct.
    private PBLoginReq(Builder builder) {
      super(builder);
    }
    private PBLoginReq(boolean noInit) {}
    
    private static final PBLoginReq defaultInstance;
    public static PBLoginReq getDefaultInstance() {
      return defaultInstance;
    }
    
    public PBLoginReq getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.game.protobuf.logic.LogicProto.internal_static_PBLoginReq_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.game.protobuf.logic.LogicProto.internal_static_PBLoginReq_fieldAccessorTable;
    }
    
    private int bitField0_;
    // required string account = 1;
    public static final int ACCOUNT_FIELD_NUMBER = 1;
    private java.lang.Object account_;
    public boolean hasAccount() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    public String getAccount() {
      java.lang.Object ref = account_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (com.google.protobuf.Internal.isValidUtf8(bs)) {
          account_ = s;
        }
        return s;
      }
    }
    private com.google.protobuf.ByteString getAccountBytes() {
      java.lang.Object ref = account_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8((String) ref);
        account_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    
    // required string playerName = 2;
    public static final int PLAYERNAME_FIELD_NUMBER = 2;
    private java.lang.Object playerName_;
    public boolean hasPlayerName() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    public String getPlayerName() {
      java.lang.Object ref = playerName_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (com.google.protobuf.Internal.isValidUtf8(bs)) {
          playerName_ = s;
        }
        return s;
      }
    }
    private com.google.protobuf.ByteString getPlayerNameBytes() {
      java.lang.Object ref = playerName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8((String) ref);
        playerName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    
    private void initFields() {
      account_ = "";
      playerName_ = "";
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;
      
      if (!hasAccount()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasPlayerName()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getAccountBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, getPlayerNameBytes());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getAccountBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, getPlayerNameBytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }
    
    public static com.game.protobuf.logic.LogicProto.PBLoginReq parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginReq parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginReq parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginReq parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginReq parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginReq parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginReq parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginReq parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginReq parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginReq parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.game.protobuf.logic.LogicProto.PBLoginReq prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.game.protobuf.logic.LogicProto.PBLoginReqOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.game.protobuf.logic.LogicProto.internal_static_PBLoginReq_descriptor;
      }
      
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.game.protobuf.logic.LogicProto.internal_static_PBLoginReq_fieldAccessorTable;
      }
      
      // Construct using com.game.protobuf.logic.LogicProto.PBLoginReq.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }
      
      private Builder(BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }
      
      public Builder clear() {
        super.clear();
        account_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        playerName_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.game.protobuf.logic.LogicProto.PBLoginReq.getDescriptor();
      }
      
      public com.game.protobuf.logic.LogicProto.PBLoginReq getDefaultInstanceForType() {
        return com.game.protobuf.logic.LogicProto.PBLoginReq.getDefaultInstance();
      }
      
      public com.game.protobuf.logic.LogicProto.PBLoginReq build() {
        com.game.protobuf.logic.LogicProto.PBLoginReq result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }
      
      private com.game.protobuf.logic.LogicProto.PBLoginReq buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        com.game.protobuf.logic.LogicProto.PBLoginReq result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return result;
      }
      
      public com.game.protobuf.logic.LogicProto.PBLoginReq buildPartial() {
        com.game.protobuf.logic.LogicProto.PBLoginReq result = new com.game.protobuf.logic.LogicProto.PBLoginReq(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.account_ = account_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.playerName_ = playerName_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.game.protobuf.logic.LogicProto.PBLoginReq) {
          return mergeFrom((com.game.protobuf.logic.LogicProto.PBLoginReq)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.game.protobuf.logic.LogicProto.PBLoginReq other) {
        if (other == com.game.protobuf.logic.LogicProto.PBLoginReq.getDefaultInstance()) return this;
        if (other.hasAccount()) {
          setAccount(other.getAccount());
        }
        if (other.hasPlayerName()) {
          setPlayerName(other.getPlayerName());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public final boolean isInitialized() {
        if (!hasAccount()) {
          
          return false;
        }
        if (!hasPlayerName()) {
          
          return false;
        }
        return true;
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
              onChanged();
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                onChanged();
                return this;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              account_ = input.readBytes();
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              playerName_ = input.readBytes();
              break;
            }
          }
        }
      }
      
      private int bitField0_;
      
      // required string account = 1;
      private java.lang.Object account_ = "";
      public boolean hasAccount() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      public String getAccount() {
        java.lang.Object ref = account_;
        if (!(ref instanceof String)) {
          String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
          account_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      public Builder setAccount(String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        account_ = value;
        onChanged();
        return this;
      }
      public Builder clearAccount() {
        bitField0_ = (bitField0_ & ~0x00000001);
        account_ = getDefaultInstance().getAccount();
        onChanged();
        return this;
      }
      void setAccount(com.google.protobuf.ByteString value) {
        bitField0_ |= 0x00000001;
        account_ = value;
        onChanged();
      }
      
      // required string playerName = 2;
      private java.lang.Object playerName_ = "";
      public boolean hasPlayerName() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      public String getPlayerName() {
        java.lang.Object ref = playerName_;
        if (!(ref instanceof String)) {
          String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
          playerName_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      public Builder setPlayerName(String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        playerName_ = value;
        onChanged();
        return this;
      }
      public Builder clearPlayerName() {
        bitField0_ = (bitField0_ & ~0x00000002);
        playerName_ = getDefaultInstance().getPlayerName();
        onChanged();
        return this;
      }
      void setPlayerName(com.google.protobuf.ByteString value) {
        bitField0_ |= 0x00000002;
        playerName_ = value;
        onChanged();
      }
      
      // @@protoc_insertion_point(builder_scope:PBLoginReq)
    }
    
    static {
      defaultInstance = new PBLoginReq(true);
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:PBLoginReq)
  }
  
  public interface PBLoginRespOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
    
    // required int64 playerId = 1;
    boolean hasPlayerId();
    long getPlayerId();
  }
  public static final class PBLoginResp extends
      com.google.protobuf.GeneratedMessage
      implements PBLoginRespOrBuilder {
    // Use PBLoginResp.newBuilder() to construct.
    private PBLoginResp(Builder builder) {
      super(builder);
    }
    private PBLoginResp(boolean noInit) {}
    
    private static final PBLoginResp defaultInstance;
    public static PBLoginResp getDefaultInstance() {
      return defaultInstance;
    }
    
    public PBLoginResp getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.game.protobuf.logic.LogicProto.internal_static_PBLoginResp_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.game.protobuf.logic.LogicProto.internal_static_PBLoginResp_fieldAccessorTable;
    }
    
    private int bitField0_;
    // required int64 playerId = 1;
    public static final int PLAYERID_FIELD_NUMBER = 1;
    private long playerId_;
    public boolean hasPlayerId() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    public long getPlayerId() {
      return playerId_;
    }
    
    private void initFields() {
      playerId_ = 0L;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;
      
      if (!hasPlayerId()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt64(1, playerId_);
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, playerId_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }
    
    public static com.game.protobuf.logic.LogicProto.PBLoginResp parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginResp parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginResp parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginResp parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginResp parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginResp parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginResp parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginResp parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginResp parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.game.protobuf.logic.LogicProto.PBLoginResp parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.game.protobuf.logic.LogicProto.PBLoginResp prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.game.protobuf.logic.LogicProto.PBLoginRespOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.game.protobuf.logic.LogicProto.internal_static_PBLoginResp_descriptor;
      }
      
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.game.protobuf.logic.LogicProto.internal_static_PBLoginResp_fieldAccessorTable;
      }
      
      // Construct using com.game.protobuf.logic.LogicProto.PBLoginResp.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }
      
      private Builder(BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }
      
      public Builder clear() {
        super.clear();
        playerId_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.game.protobuf.logic.LogicProto.PBLoginResp.getDescriptor();
      }
      
      public com.game.protobuf.logic.LogicProto.PBLoginResp getDefaultInstanceForType() {
        return com.game.protobuf.logic.LogicProto.PBLoginResp.getDefaultInstance();
      }
      
      public com.game.protobuf.logic.LogicProto.PBLoginResp build() {
        com.game.protobuf.logic.LogicProto.PBLoginResp result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }
      
      private com.game.protobuf.logic.LogicProto.PBLoginResp buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        com.game.protobuf.logic.LogicProto.PBLoginResp result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return result;
      }
      
      public com.game.protobuf.logic.LogicProto.PBLoginResp buildPartial() {
        com.game.protobuf.logic.LogicProto.PBLoginResp result = new com.game.protobuf.logic.LogicProto.PBLoginResp(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.playerId_ = playerId_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.game.protobuf.logic.LogicProto.PBLoginResp) {
          return mergeFrom((com.game.protobuf.logic.LogicProto.PBLoginResp)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.game.protobuf.logic.LogicProto.PBLoginResp other) {
        if (other == com.game.protobuf.logic.LogicProto.PBLoginResp.getDefaultInstance()) return this;
        if (other.hasPlayerId()) {
          setPlayerId(other.getPlayerId());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public final boolean isInitialized() {
        if (!hasPlayerId()) {
          
          return false;
        }
        return true;
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
              onChanged();
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                onChanged();
                return this;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              playerId_ = input.readInt64();
              break;
            }
          }
        }
      }
      
      private int bitField0_;
      
      // required int64 playerId = 1;
      private long playerId_ ;
      public boolean hasPlayerId() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      public long getPlayerId() {
        return playerId_;
      }
      public Builder setPlayerId(long value) {
        bitField0_ |= 0x00000001;
        playerId_ = value;
        onChanged();
        return this;
      }
      public Builder clearPlayerId() {
        bitField0_ = (bitField0_ & ~0x00000001);
        playerId_ = 0L;
        onChanged();
        return this;
      }
      
      // @@protoc_insertion_point(builder_scope:PBLoginResp)
    }
    
    static {
      defaultInstance = new PBLoginResp(true);
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:PBLoginResp)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_PBLoginReq_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_PBLoginReq_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_PBLoginResp_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_PBLoginResp_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021proto/Logic.proto\"1\n\nPBLoginReq\022\017\n\007acc" +
      "ount\030\001 \002(\t\022\022\n\nplayerName\030\002 \002(\t\"\037\n\013PBLogi" +
      "nResp\022\020\n\010playerId\030\001 \002(\003B%\n\027com.game.prot" +
      "obuf.logicB\nLogicProto"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_PBLoginReq_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_PBLoginReq_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_PBLoginReq_descriptor,
              new java.lang.String[] { "Account", "PlayerName", },
              com.game.protobuf.logic.LogicProto.PBLoginReq.class,
              com.game.protobuf.logic.LogicProto.PBLoginReq.Builder.class);
          internal_static_PBLoginResp_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_PBLoginResp_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_PBLoginResp_descriptor,
              new java.lang.String[] { "PlayerId", },
              com.game.protobuf.logic.LogicProto.PBLoginResp.class,
              com.game.protobuf.logic.LogicProto.PBLoginResp.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  // @@protoc_insertion_point(outer_class_scope)
}
