/*
 * Copyright @ 2018 - present 8x8, Inc.
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

package org.jitsi.nlj.resources.srtp_samples

import org.jitsi.nlj.srtp.SrtpProfileInformation
import org.jitsi.nlj.srtp.TlsRole
import org.jitsi.rtp.UnparsedPacket
import org.jitsi.rtp.rtcp.RtcpPacket
import org.jitsi.rtp.rtp.RtpPacket
import org.jitsi.rtp.util.byteBufferOf

/**
 * Contains all the information needed for instantiating SRTP and SRTCP transformers
 * and the following before and after packets:
 * SRTCP -> RTCP
 * RTCP -> RTCP
 * SRTP -> RTP
 * RTP -> RTCP
 * which can be used to test encryption and decryption
 */
class SrtpSample {
    companion object {
        val srtpProfileInformation = SrtpProfileInformation(
                cipherKeyLength = 16, cipherSaltLength = 14, cipherName = 1,
                authFunctionName = 1, authKeyLength = 20,
                rtcpAuthTagLength = 10, rtpAuthTagLength = 10
        )
        val keyingMaterial = byteBufferOf(
            0xB4, 0x04, 0x3B, 0x87, 0x67, 0xF6, 0xC4, 0x67,
            0xB2, 0x3E, 0xE1, 0xBE, 0x0C, 0xEB, 0x8E, 0x24,
            0xA0, 0x4F, 0xA4, 0x36, 0xC4, 0x17, 0x87, 0xF5,
            0xF5, 0x0C, 0xE4, 0x1A, 0x39, 0xFC, 0xB8, 0x21,
            0xDD, 0xC5, 0x60, 0x46, 0xCE, 0x69, 0x63, 0x55,
            0x8E, 0xF1, 0x9A, 0x35, 0x73, 0x0A, 0x4B, 0x69,
            0x17, 0x80, 0xD8, 0x96, 0x19, 0x85, 0xD0, 0xEF,
            0x32, 0x00, 0xCC, 0x27
        )

        val tlsRole = TlsRole.CLIENT

        // Before and after packets

        // SRTP -> RTP
        private val incomingEncryptedRtpData = org.jitsi.rtp.extensions.bytearray.byteArrayOf(
            0x90, 0xEF, 0x43, 0xD7, 0xCF, 0x6F, 0xDE, 0x8F,
            0x56, 0x29, 0x97, 0x7A, 0xBE, 0xDE, 0x00, 0x01,
            0x10, 0xFF, 0x00, 0x00, 0x40, 0xC9, 0xDC, 0x4E,
            0xDD, 0x95, 0x80, 0x41, 0x88, 0x8E, 0xFA, 0x32,
            0x1C, 0x42, 0xB4, 0x03, 0x8B, 0x2D, 0xE5, 0x79,
            0x61, 0xE2, 0x23, 0x7E, 0x17, 0x9C, 0x6E, 0xD7,
            0x6C, 0x6A, 0x11, 0x0D, 0x44, 0x91, 0x33, 0xBE,
            0xE1, 0xD7, 0x0D, 0x41, 0xE4, 0x8B
        )
        val incomingEncryptedRtpPacket = RtpPacket(incomingEncryptedRtpData, 0, incomingEncryptedRtpData.size)

        val expectedDecryptedRtpPacket = RtpPacket(org.jitsi.rtp.extensions.bytearray.byteArrayOf(
            0x90, 0xEF, 0x43, 0xD7, 0xCF, 0x6F, 0xDE, 0x8F,
            0x56, 0x29, 0x97, 0x7A, 0xBE, 0xDE, 0x00, 0x01,
            0x10, 0xFF, 0x00, 0x00, 0x78, 0x0B, 0xE4, 0xC1,
            0x36, 0xEC, 0xC5, 0x8D, 0x8C, 0x49, 0x46, 0x99,
            0x04, 0xC5, 0xAA, 0xED, 0x92, 0xE7, 0x63, 0x4A,
            0x3A, 0x18, 0x98, 0xEE, 0x62, 0xCB, 0x60, 0xFF,
            0x6C, 0x1B, 0x29, 0x00
        ))

        // SRTCP -> RTCP
        // NOTE(brian): this ended up being a compound RTCP packet,
        // so there are multiple in here
        private val incomingEncryptedRtcpData = org.jitsi.rtp.extensions.bytearray.byteArrayOf(
            0x80, 0xC8, 0x00, 0x06, 0x75, 0x6D, 0x56, 0x40,
            0x0C, 0x24, 0x4E, 0x44, 0xF9, 0xE1, 0x4A, 0x5E,
            0x1C, 0xD1, 0xEA, 0x0B, 0xB3, 0xC5, 0x34, 0xD1,
            0xF7, 0x3D, 0x5F, 0x44, 0x2B, 0x9A, 0xD9, 0x04,
            0xB0, 0xC1, 0x48, 0xF6, 0x22, 0x5F, 0x2E, 0xFE,
            0x3A, 0xD9, 0x5B, 0xA5, 0x77, 0x52, 0xE0, 0xFD,
            0x8F, 0x46, 0x9C, 0x96, 0x29, 0xE9, 0x64, 0x1A,
            0x80, 0x00, 0x00, 0x01, 0x3C, 0xB4, 0xC8, 0xE6,
            0xB8, 0x19, 0xFB, 0xEE, 0xCE, 0xA2
        )
        val incomingEncryptedRtcpPacket =
            UnparsedPacket(incomingEncryptedRtcpData, 0, incomingEncryptedRtcpData.size)

        val expectedDecryptedRtcpPacket = UnparsedPacket(org.jitsi.rtp.extensions.bytearray.byteArrayOf(
            0x80, 0xC8, 0x00, 0x06, 0x75, 0x6D, 0x56, 0x40,
            0xE0, 0x0E, 0x30, 0x93, 0x24, 0x77, 0x57, 0x4F,
            0xD3, 0x16, 0xD8, 0x0E, 0x00, 0x00, 0x00, 0x16,
            0x00, 0x00, 0x1F, 0x18, 0x81, 0xCA, 0x00, 0x06,
            0x75, 0x6D, 0x56, 0x40, 0x01, 0x10, 0x4C, 0x51,
            0x47, 0x4C, 0x32, 0x35, 0x4E, 0x76, 0x33, 0x35,
            0x30, 0x57, 0x7A, 0x38, 0x72, 0x7A, 0x00, 0x00
        ))

        // RTP -> SRTP
        private val outgoingUnencryptedRtpData = org.jitsi.rtp.extensions.bytearray.byteArrayOf(
            0x90, 0xEF, 0x36, 0xD6, 0x6C, 0x25, 0xF2, 0x81,
            0xCC, 0x8E, 0x67, 0xDB, 0xBE, 0xDE, 0x00, 0x03,
            0x10, 0xFF, 0x32, 0x88, 0xB7, 0xBD, 0x51, 0x00,
            0x01, 0x00, 0x00, 0x00, 0x78, 0x0B, 0xE4, 0xC1,
            0x36, 0xEC, 0xC5, 0x8D, 0x8C, 0x49, 0x46, 0x99,
            0x04, 0xC5, 0xAA, 0xED, 0x92, 0xE7, 0x63, 0x4A,
            0x3A, 0x18, 0x98, 0xEE, 0x62, 0xCB, 0x60, 0xFF,
            0x6C, 0x1B, 0x29, 0x00
        )
        val outgoingUnencryptedRtpPacket =
            RtpPacket(outgoingUnencryptedRtpData, 0, outgoingUnencryptedRtpData.size)

        val expectedEncryptedRtpData = org.jitsi.rtp.extensions.bytearray.byteArrayOf(
            0x90, 0xEF, 0x36, 0xD6, 0x6C, 0x25, 0xF2, 0x81,
            0xCC, 0x8E, 0x67, 0xDB, 0xBE, 0xDE, 0x00, 0x03,
            0x10, 0xFF, 0x32, 0x88, 0xB7, 0xBD, 0x51, 0x00,
            0x01, 0x00, 0x00, 0x00, 0x1C, 0x6D, 0xA8, 0xEC,
            0x3D, 0x3D, 0xE5, 0x70, 0x2B, 0x2E, 0xB7, 0x94,
            0x9D, 0x4E, 0x2F, 0x4C, 0x9D, 0xF1, 0x3A, 0x68,
            0x2E, 0x3D, 0xC9, 0xA3, 0xA5, 0xCE, 0x76, 0x3F,
            0xEA, 0x82, 0xBD, 0xD0, 0xB9, 0x39, 0x02, 0x5E,
            0x32, 0x11, 0xD6, 0x70, 0x13, 0x81
        )

        val expectedEncryptedRtpPacket = RtpPacket(expectedEncryptedRtpData, 0, expectedEncryptedRtpData.size)

        // RTCP -> SRTCP
        private val outgoingUnencryptedRtcpData = org.jitsi.rtp.extensions.bytearray.byteArrayOf(
            0x8F, 0xCD, 0x00, 0x05, 0xE7, 0x46, 0x52, 0x23,
            0x56, 0x29, 0x97, 0x7A, 0x00, 0x01, 0x00, 0x01,
            0x95, 0xBE, 0x64, 0x00, 0xD0, 0x00, 0x00, 0x00
        )
        val outgoingUnencryptedRtcpPacket =
            RtcpPacket.parse(outgoingUnencryptedRtcpData, 0, outgoingUnencryptedRtcpData.size)

        val expectedEncryptedRtcpPacket = UnparsedPacket(org.jitsi.rtp.extensions.bytearray.byteArrayOf(
            0x8F, 0xCD, 0x00, 0x05, 0xE7, 0x46, 0x52, 0x23,
            0x05, 0x67, 0x3D, 0xC0, 0x6C, 0x35, 0xF4, 0x40,
            0xE2, 0x2A, 0x7E, 0xCD, 0x99, 0x06, 0x5D, 0x34,
            0x80, 0x00, 0x00, 0x00, 0x48, 0x9E, 0x8E, 0xD1,
            0x8F, 0x2B, 0x76, 0xCC, 0xD7, 0x1F
        ))
    }
}
