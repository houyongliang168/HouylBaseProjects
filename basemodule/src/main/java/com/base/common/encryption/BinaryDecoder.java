package com.base.common.encryption;

public interface BinaryDecoder extends Decoder {
    byte[] decode(byte[] var1) throws DecoderException;
}
