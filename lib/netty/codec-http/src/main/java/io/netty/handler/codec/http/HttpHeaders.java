/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.AsciiString;
import io.netty.handler.codec.Headers.EntryVisitor;
import io.netty.util.internal.PlatformDependent;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static io.netty.handler.codec.http.HttpConstants.*;
import static io.netty.util.internal.ObjectUtil.checkNotNull;

/**
 * Provides the constants for the standard HTTP header names and values and
 * commonly used utility methods that accesses an {@link HttpMessage}.
 */
public abstract class HttpHeaders implements Iterable<Map.Entry<String, String>> {

    private static final byte[] HEADER_SEPERATOR = { COLON, SP };
    private static final byte[] CRLF = { CR, LF };

    public static final HttpHeaders EMPTY_HEADERS = new HttpHeaders() {
        @Override
        public String get(String name) {
            return null;
        }

        @Override
        public Integer getInt(CharSequence name) {
            return null;
        }

        @Override
        public int getInt(CharSequence name, int defaultValue) {
            return defaultValue;
        }

        @Override
        public Short getShort(CharSequence name) {
            return null;
        }

        @Override
        public short getShort(CharSequence name, short defaultValue) {
            return defaultValue;
        }

        @Override
        public Long getTimeMillis(CharSequence name) {
            return null;
        }

        @Override
        public long getTimeMillis(CharSequence name, long defaultValue) {
            return defaultValue;
        }

        @Override
        public List<String> getAll(String name) {
            return Collections.emptyList();
        }

        @Override
        public List<Entry<String, String>> entries() {
            return Collections.emptyList();
        }

        @Override
        public boolean contains(String name) {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Set<String> names() {
            return Collections.emptySet();
        }

        @Override
        public HttpHeaders add(String name, Object value) {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public HttpHeaders add(String name, Iterable<?> values) {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public HttpHeaders addInt(CharSequence name, int value) {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public HttpHeaders addShort(CharSequence name, short value) {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public HttpHeaders set(String name, Object value) {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public HttpHeaders set(String name, Iterable<?> values) {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public HttpHeaders setInt(CharSequence name, int value) {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public HttpHeaders setShort(CharSequence name, short value) {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public HttpHeaders remove(String name) {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public HttpHeaders clear() {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public Entry<CharSequence, CharSequence> forEachEntry(EntryVisitor<CharSequence> visitor) throws Exception {
            return null; // Since this is an empty header collection
        }

        @Override
        public Iterator<Entry<String, String>> iterator() {
            return entries().iterator();
        }
    };

    /**
     * @deprecated Use {@link HttpHeaderNames} instead.
     *
     * Standard HTTP header names.
     */
    @Deprecated
    public static final class Names {
        /**
         * {@code "Accept"}
         */
        public static final String ACCEPT = "Accept";
        /**
         * {@code "Accept-Charset"}
         */
        public static final String ACCEPT_CHARSET = "Accept-Charset";
        /**
         * {@code "Accept-Encoding"}
         */
        public static final String ACCEPT_ENCODING = "Accept-Encoding";
        /**
         * {@code "Accept-Language"}
         */
        public static final String ACCEPT_LANGUAGE = "Accept-Language";
        /**
         * {@code "Accept-Ranges"}
         */
        public static final String ACCEPT_RANGES = "Accept-Ranges";
        /**
         * {@code "Accept-Patch"}
         */
        public static final String ACCEPT_PATCH = "Accept-Patch";
        /**
         * {@code "Access-Control-Allow-Credentials"}
         */
        public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
        /**
         * {@code "Access-Control-Allow-Headers"}
         */
        public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
        /**
         * {@code "Access-Control-Allow-Methods"}
         */
        public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
        /**
         * {@code "Access-Control-Allow-Origin"}
         */
        public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
        /**
         * {@code "Access-Control-Expose-Headers"}
         */
        public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
        /**
         * {@code "Access-Control-Max-Age"}
         */
        public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
        /**
         * {@code "Access-Control-Request-Headers"}
         */
        public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
        /**
         * {@code "Access-Control-Request-Method"}
         */
        public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
        /**
         * {@code "Age"}
         */
        public static final String AGE = "Age";
        /**
         * {@code "Allow"}
         */
        public static final String ALLOW = "Allow";
        /**
         * {@code "Authorization"}
         */
        public static final String AUTHORIZATION = "Authorization";
        /**
         * {@code "Cache-Control"}
         */
        public static final String CACHE_CONTROL = "Cache-Control";
        /**
         * {@code "Connection"}
         */
        public static final String CONNECTION = "Connection";
        /**
         * {@code "Content-Base"}
         */
        public static final String CONTENT_BASE = "Content-Base";
        /**
         * {@code "Content-Encoding"}
         */
        public static final String CONTENT_ENCODING = "Content-Encoding";
        /**
         * {@code "Content-Language"}
         */
        public static final String CONTENT_LANGUAGE = "Content-Language";
        /**
         * {@code "Content-Length"}
         */
        public static final String CONTENT_LENGTH = "Content-Length";
        /**
         * {@code "Content-Location"}
         */
        public static final String CONTENT_LOCATION = "Content-Location";
        /**
         * {@code "Content-Transfer-Encoding"}
         */
        public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
        /**
         * {@code "Content-MD5"}
         */
        public static final String CONTENT_MD5 = "Content-MD5";
        /**
         * {@code "Content-Range"}
         */
        public static final String CONTENT_RANGE = "Content-Range";
        /**
         * {@code "Content-Type"}
         */
        public static final String CONTENT_TYPE = "Content-Type";
        /**
         * {@code "Cookie"}
         */
        public static final String COOKIE = "Cookie";
        /**
         * {@code "Date"}
         */
        public static final String DATE = "Date";
        /**
         * {@code "ETag"}
         */
        public static final String ETAG = "ETag";
        /**
         * {@code "Expect"}
         */
        public static final String EXPECT = "Expect";
        /**
         * {@code "Expires"}
         */
        public static final String EXPIRES = "Expires";
        /**
         * {@code "From"}
         */
        public static final String FROM = "From";
        /**
         * {@code "Host"}
         */
        public static final String HOST = "Host";
        /**
         * {@code "If-Match"}
         */
        public static final String IF_MATCH = "If-Match";
        /**
         * {@code "If-Modified-Since"}
         */
        public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
        /**
         * {@code "If-None-Match"}
         */
        public static final String IF_NONE_MATCH = "If-None-Match";
        /**
         * {@code "If-Range"}
         */
        public static final String IF_RANGE = "If-Range";
        /**
         * {@code "If-Unmodified-Since"}
         */
        public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
        /**
         * {@code "Last-Modified"}
         */
        public static final String LAST_MODIFIED = "Last-Modified";
        /**
         * {@code "Location"}
         */
        public static final String LOCATION = "Location";
        /**
         * {@code "Max-Forwards"}
         */
        public static final String MAX_FORWARDS = "Max-Forwards";
        /**
         * {@code "Origin"}
         */
        public static final String ORIGIN = "Origin";
        /**
         * {@code "Pragma"}
         */
        public static final String PRAGMA = "Pragma";
        /**
         * {@code "Proxy-Authenticate"}
         */
        public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
        /**
         * {@code "Proxy-Authorization"}
         */
        public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
        /**
         * {@code "Range"}
         */
        public static final String RANGE = "Range";
        /**
         * {@code "Referer"}
         */
        public static final String REFERER = "Referer";
        /**
         * {@code "Retry-After"}
         */
        public static final String RETRY_AFTER = "Retry-After";
        /**
         * {@code "Sec-WebSocket-Key1"}
         */
        public static final String SEC_WEBSOCKET_KEY1 = "Sec-WebSocket-Key1";
        /**
         * {@code "Sec-WebSocket-Key2"}
         */
        public static final String SEC_WEBSOCKET_KEY2 = "Sec-WebSocket-Key2";
        /**
         * {@code "Sec-WebSocket-Location"}
         */
        public static final String SEC_WEBSOCKET_LOCATION = "Sec-WebSocket-Location";
        /**
         * {@code "Sec-WebSocket-Origin"}
         */
        public static final String SEC_WEBSOCKET_ORIGIN = "Sec-WebSocket-Origin";
        /**
         * {@code "Sec-WebSocket-Protocol"}
         */
        public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
        /**
         * {@code "Sec-WebSocket-Version"}
         */
        public static final String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
        /**
         * {@code "Sec-WebSocket-Key"}
         */
        public static final String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
        /**
         * {@code "Sec-WebSocket-Accept"}
         */
        public static final String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";
        /**
         * {@code "Server"}
         */
        public static final String SERVER = "Server";
        /**
         * {@code "Set-Cookie"}
         */
        public static final String SET_COOKIE = "Set-Cookie";
        /**
         * {@code "Set-Cookie2"}
         */
        public static final String SET_COOKIE2 = "Set-Cookie2";
        /**
         * {@code "TE"}
         */
        public static final String TE = "TE";
        /**
         * {@code "Trailer"}
         */
        public static final String TRAILER = "Trailer";
        /**
         * {@code "Transfer-Encoding"}
         */
        public static final String TRANSFER_ENCODING = "Transfer-Encoding";
        /**
         * {@code "Upgrade"}
         */
        public static final String UPGRADE = "Upgrade";
        /**
         * {@code "User-Agent"}
         */
        public static final String USER_AGENT = "User-Agent";
        /**
         * {@code "Vary"}
         */
        public static final String VARY = "Vary";
        /**
         * {@code "Via"}
         */
        public static final String VIA = "Via";
        /**
         * {@code "Warning"}
         */
        public static final String WARNING = "Warning";
        /**
         * {@code "WebSocket-Location"}
         */
        public static final String WEBSOCKET_LOCATION = "WebSocket-Location";
        /**
         * {@code "WebSocket-Origin"}
         */
        public static final String WEBSOCKET_ORIGIN = "WebSocket-Origin";
        /**
         * {@code "WebSocket-Protocol"}
         */
        public static final String WEBSOCKET_PROTOCOL = "WebSocket-Protocol";
        /**
         * {@code "WWW-Authenticate"}
         */
        public static final String WWW_AUTHENTICATE = "WWW-Authenticate";

        private Names() {
        }
    }

    /**
     * @deprecated Use {@link HttpHeaderValues} instead.
     *
     * Standard HTTP header values.
     */
    @Deprecated
    public static final class Values {
        /**
         * {@code "application/x-www-form-urlencoded"}
         */
        public static final String APPLICATION_X_WWW_FORM_URLENCODED =
            "application/x-www-form-urlencoded";
        /**
         * {@code "base64"}
         */
        public static final String BASE64 = "base64";
        /**
         * {@code "binary"}
         */
        public static final String BINARY = "binary";
        /**
         * {@code "boundary"}
         */
        public static final String BOUNDARY = "boundary";
        /**
         * {@code "bytes"}
         */
        public static final String BYTES = "bytes";
        /**
         * {@code "charset"}
         */
        public static final String CHARSET = "charset";
        /**
         * {@code "chunked"}
         */
        public static final String CHUNKED = "chunked";
        /**
         * {@code "close"}
         */
        public static final String CLOSE = "close";
        /**
         * {@code "compress"}
         */
        public static final String COMPRESS = "compress";
        /**
         * {@code "100-continue"}
         */
        public static final String CONTINUE =  "100-continue";
        /**
         * {@code "deflate"}
         */
        public static final String DEFLATE = "deflate";
        /**
         * {@code "gzip"}
         */
        public static final String GZIP = "gzip";
        /**
         * {@code "identity"}
         */
        public static final String IDENTITY = "identity";
        /**
         * {@code "keep-alive"}
         */
        public static final String KEEP_ALIVE = "keep-alive";
        /**
         * {@code "max-age"}
         */
        public static final String MAX_AGE = "max-age";
        /**
         * {@code "max-stale"}
         */
        public static final String MAX_STALE = "max-stale";
        /**
         * {@code "min-fresh"}
         */
        public static final String MIN_FRESH = "min-fresh";
        /**
         * {@code "multipart/form-data"}
         */
        public static final String MULTIPART_FORM_DATA = "multipart/form-data";
        /**
         * {@code "must-revalidate"}
         */
        public static final String MUST_REVALIDATE = "must-revalidate";
        /**
         * {@code "no-cache"}
         */
        public static final String NO_CACHE = "no-cache";
        /**
         * {@code "no-store"}
         */
        public static final String NO_STORE = "no-store";
        /**
         * {@code "no-transform"}
         */
        public static final String NO_TRANSFORM = "no-transform";
        /**
         * {@code "none"}
         */
        public static final String NONE = "none";
        /**
         * {@code "only-if-cached"}
         */
        public static final String ONLY_IF_CACHED = "only-if-cached";
        /**
         * {@code "private"}
         */
        public static final String PRIVATE = "private";
        /**
         * {@code "proxy-revalidate"}
         */
        public static final String PROXY_REVALIDATE = "proxy-revalidate";
        /**
         * {@code "public"}
         */
        public static final String PUBLIC = "public";
        /**
         * {@code "quoted-printable"}
         */
        public static final String QUOTED_PRINTABLE = "quoted-printable";
        /**
         * {@code "s-maxage"}
         */
        public static final String S_MAXAGE = "s-maxage";
        /**
         * {@code "trailers"}
         */
        public static final String TRAILERS = "trailers";
        /**
         * {@code "Upgrade"}
         */
        public static final String UPGRADE = "Upgrade";
        /**
         * {@code "WebSocket"}
         */
        public static final String WEBSOCKET = "WebSocket";

        private Values() {
        }
    }

    /**
     * @deprecated Use {@link HttpHeaderUtil#isKeepAlive(HttpMessage)} instead.
     *
     * Returns {@code true} if and only if the connection can remain open and
     * thus 'kept alive'.  This methods respects the value of the
     * {@code "Connection"} header first and then the return value of
     * {@link HttpVersion#isKeepAliveDefault()}.
     */
    @Deprecated
    public static boolean isKeepAlive(HttpMessage message) {
        return HttpHeaderUtil.isKeepAlive(message);
    }

    /**
     * @deprecated Use {@link HttpHeaderUtil#setKeepAlive(HttpMessage, boolean)} instead.
     *
     * Sets the value of the {@code "Connection"} header depending on the
     * protocol version of the specified message.  This getMethod sets or removes
     * the {@code "Connection"} header depending on what the default keep alive
     * mode of the message's protocol version is, as specified by
     * {@link HttpVersion#isKeepAliveDefault()}.
     * <ul>
     * <li>If the connection is kept alive by default:
     *     <ul>
     *     <li>set to {@code "close"} if {@code keepAlive} is {@code false}.</li>
     *     <li>remove otherwise.</li>
     *     </ul></li>
     * <li>If the connection is closed by default:
     *     <ul>
     *     <li>set to {@code "keep-alive"} if {@code keepAlive} is {@code true}.</li>
     *     <li>remove otherwise.</li>
     *     </ul></li>
     * </ul>
     */
    @Deprecated
    public static void setKeepAlive(HttpMessage message, boolean keepAlive) {
        HttpHeaderUtil.setKeepAlive(message, keepAlive);
    }

    /**
     * @deprecated Use {@link #get(CharSequence)} instead.
     *
     * @see {@link #getHeader(HttpMessage, CharSequence)}
     */
    @Deprecated
    public static String getHeader(HttpMessage message, String name) {
        return message.headers().get(name);
    }

    /**
     * @deprecated Use {@link #get(CharSequence)} instead.
     *
     * Returns the header value with the specified header name.  If there are
     * more than one header value for the specified header name, the first
     * value is returned.
     *
     * @return the header value or {@code null} if there is no such header
     */
    @Deprecated
    public static String getHeader(HttpMessage message, CharSequence name) {
        return message.headers().get(name);
    }

    /**
     * @deprecated Use {@link #get(CharSequence, String)} instead.
     *
     * @see {@link #getHeader(HttpMessage, CharSequence, String)}
     */
    @Deprecated
    public static String getHeader(HttpMessage message, String name, String defaultValue) {
        return message.headers().get(name, defaultValue);
    }

    /**
     * @deprecated Use {@link #get(CharSequence, String)} instead.
     *
     * Returns the header value with the specified header name.  If there are
     * more than one header value for the specified header name, the first
     * value is returned.
     *
     * @return the header value or the {@code defaultValue} if there is no such
     *         header
     */
    @Deprecated
    public static String getHeader(HttpMessage message, CharSequence name, String defaultValue) {
        return message.headers().get(name, defaultValue);
    }

    /**
     * @deprecated Use {@link #set(CharSequence, Object)} instead.
     *
     * @see {@link #setHeader(HttpMessage, CharSequence, Object)}
     */
    @Deprecated
    public static void setHeader(HttpMessage message, String name, Object value) {
        message.headers().set(name, value);
    }

    /**
     * @deprecated Use {@link #set(CharSequence, Object)} instead.
     *
     * Sets a new header with the specified name and value.  If there is an
     * existing header with the same name, the existing header is removed.
     * If the specified value is not a {@link String}, it is converted into a
     * {@link String} by {@link Object#toString()}, except for {@link Date}
     * and {@link Calendar} which are formatted to the date format defined in
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1">RFC2616</a>.
     */
    @Deprecated
    public static void setHeader(HttpMessage message, CharSequence name, Object value) {
        message.headers().set(name, value);
    }

    /**
     * @deprecated Use {@link #set(CharSequence, Iterable)} instead.
     *
     * @see {@link #setHeader(HttpMessage, CharSequence, Iterable)}
     */
    @Deprecated
    public static void setHeader(HttpMessage message, String name, Iterable<?> values) {
        message.headers().set(name, values);
    }

    /**
     * @deprecated Use {@link #set(CharSequence, Iterable)} instead.
     *
     * Sets a new header with the specified name and values.  If there is an
     * existing header with the same name, the existing header is removed.
     * This getMethod can be represented approximately as the following code:
     * <pre>
     * removeHeader(message, name);
     * for (Object v: values) {
     *     if (v == null) {
     *         break;
     *     }
     *     addHeader(message, name, v);
     * }
     * </pre>
     */
    @Deprecated
    public static void setHeader(HttpMessage message, CharSequence name, Iterable<?> values) {
        message.headers().set(name, values);
    }

    /**
     * @deprecated Use {@link #add(CharSequence, Object)} instead.
     *
     * @see {@link #addHeader(HttpMessage, CharSequence, Object)}
     */
    @Deprecated
    public static void addHeader(HttpMessage message, String name, Object value) {
        message.headers().add(name, value);
    }

    /**
     * @deprecated Use {@link #add(CharSequence, Object)} instead.
     *
     * Adds a new header with the specified name and value.
     * If the specified value is not a {@link String}, it is converted into a
     * {@link String} by {@link Object#toString()}, except for {@link Date}
     * and {@link Calendar} which are formatted to the date format defined in
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1">RFC2616</a>.
     */
    @Deprecated
    public static void addHeader(HttpMessage message, CharSequence name, Object value) {
        message.headers().add(name, value);
    }

    /**
     * @deprecated Use {@link #remove(CharSequence)} instead.
     *
     * @see {@link #removeHeader(HttpMessage, CharSequence)}
     */
    @Deprecated
    public static void removeHeader(HttpMessage message, String name) {
        message.headers().remove(name);
    }

    /**
     * @deprecated Use {@link #remove(CharSequence)} instead.
     *
     * Removes the header with the specified name.
     */
    @Deprecated
    public static void removeHeader(HttpMessage message, CharSequence name) {
        message.headers().remove(name);
    }

    /**
     * @deprecated Use {@link #clear()} instead.
     *
     * Removes all headers from the specified message.
     */
    @Deprecated
    public static void clearHeaders(HttpMessage message) {
        message.headers().clear();
    }

    /**
     * @deprecated Use {@link #getInt(CharSequence)} instead.
     *
     * @see {@link #getIntHeader(HttpMessage, CharSequence)}
     */
    @Deprecated
    public static int getIntHeader(HttpMessage message, String name) {
        return getIntHeader(message, (CharSequence) name);
    }

    /**
     * @deprecated Use {@link #getInt(CharSequence)} instead.
     *
     * Returns the integer header value with the specified header name.  If
     * there are more than one header value for the specified header name, the
     * first value is returned.
     *
     * @return the header value
     * @throws NumberFormatException
     *         if there is no such header or the header value is not a number
     */
    @Deprecated
    public static int getIntHeader(HttpMessage message, CharSequence name) {
        String value = message.headers().get(name);
        if (value == null) {
            throw new NumberFormatException("header not found: " + name);
        }
        return Integer.parseInt(value);
    }

    /**
     * @deprecated Use {@link #getInt(CharSequence, int)} instead.
     *
     * @see {@link #getIntHeader(HttpMessage, CharSequence, int)}
     */
    @Deprecated
    public static int getIntHeader(HttpMessage message, String name, int defaultValue) {
        return message.headers().getInt(name, defaultValue);
    }

    /**
     * @deprecated Use {@link #getInt(CharSequence, int)} instead.
     *
     * Returns the integer header value with the specified header name.  If
     * there are more than one header value for the specified header name, the
     * first value is returned.
     *
     * @return the header value or the {@code defaultValue} if there is no such
     *         header or the header value is not a number
     */
    @Deprecated
    public static int getIntHeader(HttpMessage message, CharSequence name, int defaultValue) {
        return message.headers().getInt(name, defaultValue);
    }

    /**
     * @deprecated Use {@link #setInt(CharSequence, int)} instead.
     *
     * @see {@link #setIntHeader(HttpMessage, CharSequence, int)}
     */
    @Deprecated
    public static void setIntHeader(HttpMessage message, String name, int value) {
        message.headers().setInt(name, value);
    }

    /**
     * @deprecated Use {@link #setInt(CharSequence, int)} instead.
     *
     * Sets a new integer header with the specified name and value.  If there
     * is an existing header with the same name, the existing header is removed.
     */
    @Deprecated
    public static void setIntHeader(HttpMessage message, CharSequence name, int value) {
        message.headers().setInt(name, value);
    }

    /**
     * @deprecated Use {@link #set(CharSequence, Iterable)} instead.
     *
     * @see {@link #setIntHeader(HttpMessage, CharSequence, Iterable)}
     */
    @Deprecated
    public static void setIntHeader(HttpMessage message, String name, Iterable<Integer> values) {
        message.headers().set(name, values);
    }

    /**
     * @deprecated Use {@link #set(CharSequence, Iterable)} instead.
     *
     * Sets a new integer header with the specified name and values.  If there
     * is an existing header with the same name, the existing header is removed.
     */
    @Deprecated
    public static void setIntHeader(HttpMessage message, CharSequence name, Iterable<Integer> values) {
        message.headers().set(name, values);
    }

    /**
     * @deprecated Use {@link #add(CharSequence, Iterable)} instead.
     *
     * @see {@link #addIntHeader(HttpMessage, CharSequence, int)}
     */
    @Deprecated
    public static void addIntHeader(HttpMessage message, String name, int value) {
        message.headers().add(name, value);
    }

    /**
     * @deprecated Use {@link #addInt(CharSequence, int)} instead.
     *
     * Adds a new integer header with the specified name and value.
     */
    @Deprecated
    public static void addIntHeader(HttpMessage message, CharSequence name, int value) {
        message.headers().addInt(name, value);
    }

    /**
     * @deprecated Use {@link #getTimeMillis(CharSequence)} instead.
     *
     * @see {@link #getDateHeader(HttpMessage, CharSequence)}
     */
    @Deprecated
    public static Date getDateHeader(HttpMessage message, String name) throws ParseException {
        return getDateHeader(message, (CharSequence) name);
    }

    /**
     * @deprecated Use {@link #getTimeMillis(CharSequence)} instead.
     *
     * Returns the date header value with the specified header name.  If
     * there are more than one header value for the specified header name, the
     * first value is returned.
     *
     * @return the header value
     * @throws ParseException
     *         if there is no such header or the header value is not a formatted date
     */
    @Deprecated
    public static Date getDateHeader(HttpMessage message, CharSequence name) throws ParseException {
        String value = message.headers().get(name);
        if (value == null) {
            throw new ParseException("header not found: " + name, 0);
        }
        return HttpHeaderDateFormat.get().parse(value);
    }

    /**
     * @deprecated Use {@link #getTimeMillis(CharSequence, long)} instead.
     *
     * @see {@link #getDateHeader(HttpMessage, CharSequence, Date)}
     */
    @Deprecated
    public static Date getDateHeader(HttpMessage message, String name, Date defaultValue) {
        return getDateHeader(message, (CharSequence) name, defaultValue);
    }

    /**
     * @deprecated Use {@link #getTimeMillis(CharSequence, long)} instead.
     *
     * Returns the date header value with the specified header name.  If
     * there are more than one header value for the specified header name, the
     * first value is returned.
     *
     * @return the header value or the {@code defaultValue} if there is no such
     *         header or the header value is not a formatted date
     */
    @Deprecated
    public static Date getDateHeader(HttpMessage message, CharSequence name, Date defaultValue) {
        final String value = getHeader(message, name);
        if (value == null) {
            return defaultValue;
        }

        try {
            return HttpHeaderDateFormat.get().parse(value);
        } catch (ParseException ignored) {
            return defaultValue;
        }
    }

    /**
     * @deprecated Use {@link #set(CharSequence, Object)} instead.
     *
     * @see {@link #setDateHeader(HttpMessage, CharSequence, Date)}
     */
    @Deprecated
    public static void setDateHeader(HttpMessage message, String name, Date value) {
        setDateHeader(message, (CharSequence) name, value);
    }

    /**
     * @deprecated Use {@link #set(CharSequence, Object)} instead.
     *
     * Sets a new date header with the specified name and value.  If there
     * is an existing header with the same name, the existing header is removed.
     * The specified value is formatted as defined in
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1">RFC2616</a>
     */
    @Deprecated
    public static void setDateHeader(HttpMessage message, CharSequence name, Date value) {
        if (value != null) {
            message.headers().set(name, HttpHeaderDateFormat.get().format(value));
        } else {
            message.headers().set(name, null);
        }
    }

    /**
     * @deprecated Use {@link #set(CharSequence, Iterable)} instead.
     *
     * @see {@link #setDateHeader(HttpMessage, CharSequence, Iterable)}
     */
    @Deprecated
    public static void setDateHeader(HttpMessage message, String name, Iterable<Date> values) {
        message.headers().set(name, values);
    }

    /**
     * @deprecated Use {@link #set(CharSequence, Iterable)} instead.
     *
     * Sets a new date header with the specified name and values.  If there
     * is an existing header with the same name, the existing header is removed.
     * The specified values are formatted as defined in
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1">RFC2616</a>
     */
    @Deprecated
    public static void setDateHeader(HttpMessage message, CharSequence name, Iterable<Date> values) {
        message.headers().set(name, values);
    }

    /**
     * @deprecated Use {@link #add(CharSequence, Object)} instead.
     *
     * @see {@link #addDateHeader(HttpMessage, CharSequence, Date)}
     */
    @Deprecated
    public static void addDateHeader(HttpMessage message, String name, Date value) {
        message.headers().add(name, value);
    }

    /**
     * @deprecated Use {@link #add(CharSequence, Object)} instead.
     *
     * Adds a new date header with the specified name and value.  The specified
     * value is formatted as defined in
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1">RFC2616</a>
     */
    @Deprecated
    public static void addDateHeader(HttpMessage message, CharSequence name, Date value) {
        message.headers().add(name, value);
    }

    /**
     * @deprecated Use {@link HttpHeaderUtil#getContentLength(HttpMessage)} instead.
     *
     * Returns the length of the content.  Please note that this value is
     * not retrieved from {@link HttpContent#content()} but from the
     * {@code "Content-Length"} header, and thus they are independent from each
     * other.
     *
     * @return the content length
     *
     * @throws NumberFormatException
     *         if the message does not have the {@code "Content-Length"} header
     *         or its value is not a number
     */
    @Deprecated
    public static long getContentLength(HttpMessage message) {
        return HttpHeaderUtil.getContentLength(message);
    }

    /**
     * @deprecated Use {@link HttpHeaderUtil#getContentLength(HttpMessage, long)} instead.
     *
     * Returns the length of the content.  Please note that this value is
     * not retrieved from {@link HttpContent#content()} but from the
     * {@code "Content-Length"} header, and thus they are independent from each
     * other.
     *
     * @return the content length or {@code defaultValue} if this message does
     *         not have the {@code "Content-Length"} header or its value is not
     *         a number
     */
    @Deprecated
    public static long getContentLength(HttpMessage message, long defaultValue) {
        return HttpHeaderUtil.getContentLength(message, defaultValue);
    }

    /**
     * @deprecated Use {@link HttpHeaderUtil#setContentLength(HttpMessage, long)} instead.
     */
    @Deprecated
    public static void setContentLength(HttpMessage message, long length) {
        HttpHeaderUtil.setContentLength(message, length);
    }

    /**
     * @deprecated Use {@link #get(CharSequence)} instead.
     *
     * Returns the value of the {@code "Host"} header.
     */
    @Deprecated
    public static String getHost(HttpMessage message) {
        return message.headers().get(HttpHeaderNames.HOST);
    }

    /**
     * @deprecated Use {@link #get(CharSequence, String)} instead.
     *
     * Returns the value of the {@code "Host"} header.  If there is no such
     * header, the {@code defaultValue} is returned.
     */
    @Deprecated
    public static String getHost(HttpMessage message, String defaultValue) {
        return message.headers().get(HttpHeaderNames.HOST, defaultValue);
    }

    /**
     * @deprecated Use {@link #set(CharSequence, Object)} instead.
     *
     * @see {@link #setHost(HttpMessage, CharSequence)}
     */
    @Deprecated
    public static void setHost(HttpMessage message, String value) {
        message.headers().set(HttpHeaderNames.HOST, value);
    }

    /**
     * @deprecated Use {@link #set(CharSequence, Object)} instead.
     *
     * Sets the {@code "Host"} header.
     */
    @Deprecated
    public static void setHost(HttpMessage message, CharSequence value) {
        message.headers().set(HttpHeaderNames.HOST, value);
    }

    /**
     * @deprecated Use {@link #getTimeMillis(CharSequence)} instead.
     *
     * Returns the value of the {@code "Date"} header.
     *
     * @throws ParseException
     *         if there is no such header or the header value is not a formatted date
     */
    @Deprecated
    public static Date getDate(HttpMessage message) throws ParseException {
        return getDateHeader(message, HttpHeaderNames.DATE);
    }

    /**
     * @deprecated Use {@link #getTimeMillis(CharSequence, long)} instead.
     *
     * Returns the value of the {@code "Date"} header. If there is no such
     * header or the header is not a formatted date, the {@code defaultValue}
     * is returned.
     */
    @Deprecated
    public static Date getDate(HttpMessage message, Date defaultValue) {
        return getDateHeader(message, HttpHeaderNames.DATE, defaultValue);
    }

    /**
     * @deprecated Use {@link #set(CharSequence, Object)} instead.
     *
     * Sets the {@code "Date"} header.
     */
    @Deprecated
    public static void setDate(HttpMessage message, Date value) {
        message.headers().set(HttpHeaderNames.DATE, value);
    }

    /**
     * @deprecated Use {@link HttpHeaderUtil#is100ContinueExpected(HttpMessage)} instead.
     *
     * Returns {@code true} if and only if the specified message contains the
     * {@code "Expect: 100-continue"} header.
     */
    @Deprecated
    public static boolean is100ContinueExpected(HttpMessage message) {
        return HttpHeaderUtil.is100ContinueExpected(message);
    }

    /**
     * @deprecated Use {@link HttpHeaderUtil#set100ContinueExpected(HttpMessage, boolean)} instead.
     *
     * Sets the {@code "Expect: 100-continue"} header to the specified message.
     * If there is any existing {@code "Expect"} header, they are replaced with
     * the new one.
     */
    @Deprecated
    public static void set100ContinueExpected(HttpMessage message) {
        HttpHeaderUtil.set100ContinueExpected(message, true);
    }

    /**
     * @deprecated Use {@link HttpHeaderUtil#set100ContinueExpected(HttpMessage, boolean)} instead.
     *
     * Sets or removes the {@code "Expect: 100-continue"} header to / from the
     * specified message.  If the specified {@code value} is {@code true},
     * the {@code "Expect: 100-continue"} header is set and all other previous
     * {@code "Expect"} headers are removed.  Otherwise, all {@code "Expect"}
     * headers are removed completely.
     */
    @Deprecated
    public static void set100ContinueExpected(HttpMessage message, boolean set) {
        HttpHeaderUtil.set100ContinueExpected(message, set);
    }

    /**
     * @deprecated Use {@link HttpHeaderUtil#isTransferEncodingChunked(HttpMessage)} instead.
     *
     * Checks to see if the transfer encoding in a specified {@link HttpMessage} is chunked
     *
     * @param message The message to check
     * @return True if transfer encoding is chunked, otherwise false
     */
    @Deprecated
    public static boolean isTransferEncodingChunked(HttpMessage message) {
        return HttpHeaderUtil.isTransferEncodingChunked(message);
    }

    /**
     * @deprecated Use {@link HttpHeaderUtil#setTransferEncodingChunked(HttpMessage, boolean)} instead.
     */
    @Deprecated
    public static void removeTransferEncodingChunked(HttpMessage m) {
        HttpHeaderUtil.setTransferEncodingChunked(m, false);
    }

    /**
     * @deprecated Use {@link HttpHeaderUtil#setTransferEncodingChunked(HttpMessage, boolean)} instead.
     */
    @Deprecated
    public static void setTransferEncodingChunked(HttpMessage m) {
        HttpHeaderUtil.setTransferEncodingChunked(m, true);
    }

    /**
     * @deprecated Use {@link HttpHeaderUtil#isContentLengthSet(HttpMessage)} instead.
     */
    @Deprecated
    public static boolean isContentLengthSet(HttpMessage m) {
        return HttpHeaderUtil.isContentLengthSet(m);
    }

    /**
     * @deprecated Use {@link AsciiString#equalsIgnoreCase(CharSequence, CharSequence)} instead.
     */
    @Deprecated
    public static boolean equalsIgnoreCase(CharSequence name1, CharSequence name2) {
        return AsciiString.equalsIgnoreCase(name1, name2);
    }

    static void encode(HttpHeaders headers, ByteBuf buf) throws Exception {
        if (headers instanceof DefaultHttpHeaders) {
            ((DefaultHttpHeaders) headers).encode(buf);
        } else {
            for (Entry<String, String> header: headers) {
                encode(header.getKey(), header.getValue(), buf);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private static void encode(CharSequence key, CharSequence value, ByteBuf buf) {
        encodeAscii(key, buf);
        buf.writeBytes(HEADER_SEPERATOR);
        encodeAscii(value, buf);
        buf.writeBytes(CRLF);
    }

    @Deprecated
    public static void encodeAscii(CharSequence seq, ByteBuf buf) {
        if (seq instanceof AsciiString) {
            ((AsciiString) seq).copy(0, buf, seq.length());
        } else {
            encodeAscii0(seq, buf);
        }
    }

    static void encodeAscii0(CharSequence seq, ByteBuf buf) {
        int length = seq.length();
        for (int i = 0 ; i < length; i++) {
            buf.writeByte((byte) seq.charAt(i));
        }
    }

    /**
     * @deprecated Use {@link AsciiString} instead.
     *
     * Create a new {@link CharSequence} which is optimized for reuse as {@link HttpHeaders} name or value.
     * So if you have a Header name or value that you want to reuse you should make use of this.
     */
    @Deprecated
    public static CharSequence newEntity(String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        return new AsciiString(name);
    }

    protected HttpHeaders() { }

    /**
     * @see {@link #get(CharSequence)}
     */
    public abstract String get(String name);

    /**
     * Returns the value of a header with the specified name.  If there are
     * more than one values for the specified name, the first value is returned.
     *
     * @param name The name of the header to search
     * @return The first header value or {@code null} if there is no such header
     */
    public String get(CharSequence name) {
        return get(name.toString());
    }

    /**
     * Returns the value of a header with the specified name.  If there are
     * more than one values for the specified name, the first value is returned.
     *
     * @param name The name of the header to search
     * @return The first header value or {@code defaultValue} if there is no such header
     */
    public String get(CharSequence name, String defaultValue) {
        String value = get(name);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * Returns the integer value of a header with the specified name. If there are more than one values for the
     * specified name, the first value is returned.
     *
     * @param name the name of the header to search
     * @return the first header value if the header is found and its value is an integer. {@code null} if there's no
     *         such header or its value is not an integer.
     */
    public abstract Integer getInt(CharSequence name);

    /**
     * Returns the integer value of a header with the specified name. If there are more than one values for the
     * specified name, the first value is returned.
     *
     * @param name the name of the header to search
     * @param defaultValue the default value
     * @return the first header value if the header is found and its value is an integer. {@code defaultValue} if
     *         there's no such header or its value is not an integer.
     */
    public abstract int getInt(CharSequence name, int defaultValue);

    /**
     * Returns the short value of a header with the specified name. If there are more than one values for the
     * specified name, the first value is returned.
     *
     * @param name the name of the header to search
     * @return the first header value if the header is found and its value is a short. {@code null} if there's no
     *         such header or its value is not a short.
     */
    public abstract Short getShort(CharSequence name);

    /**
     * Returns the short value of a header with the specified name. If there are more than one values for the
     * specified name, the first value is returned.
     *
     * @param name the name of the header to search
     * @param defaultValue the default value
     * @return the first header value if the header is found and its value is a short. {@code defaultValue} if
     *         there's no such header or its value is not a short.
     */
    public abstract short getShort(CharSequence name, short defaultValue);

    /**
     * Returns the date value of a header with the specified name. If there are more than one values for the
     * specified name, the first value is returned.
     *
     * @param name the name of the header to search
     * @return the first header value if the header is found and its value is a date. {@code null} if there's no
     *         such header or its value is not a date.
     */
    public abstract Long getTimeMillis(CharSequence name);

    /**
     * Returns the date value of a header with the specified name. If there are more than one values for the
     * specified name, the first value is returned.
     *
     * @param name the name of the header to search
     * @param defaultValue the default value
     * @return the first header value if the header is found and its value is a date. {@code defaultValue} if
     *         there's no such header or its value is not a date.
     */
    public abstract long getTimeMillis(CharSequence name, long defaultValue);

    /**
     * @see {@link #getAll(CharSequence)}
     */
    public abstract List<String> getAll(String name);

    /**
     * Returns the values of headers with the specified name
     *
     * @param name The name of the headers to search
     * @return A {@link List} of header values which will be empty if no values
     *         are found
     */
    public List<String> getAll(CharSequence name) {
        return getAll(name.toString());
    }

    /**
     * Returns a new {@link List} that contains all headers in this object.  Note that modifying the
     * returned {@link List} will not affect the state of this object.  If you intend to enumerate over the header
     * entries only, use {@link #iterator()} instead, which has much less overhead.
     */
    public abstract List<Map.Entry<String, String>> entries();

    /**
     * @see {@link #contains(CharSequence)}
     */
    public abstract boolean contains(String name);

    /**
     * Checks to see if there is a header with the specified name
     *
     * @param name The name of the header to search for
     * @return True if at least one header is found
     */
    public boolean contains(CharSequence name) {
        return contains(name.toString());
    }

    /**
     * Checks if no header exists.
     */
    public abstract boolean isEmpty();

    /**
     * Returns a new {@link Set} that contains the names of all headers in this object.  Note that modifying the
     * returned {@link Set} will not affect the state of this object.  If you intend to enumerate over the header
     * entries only, use {@link #iterator()} instead, which has much less overhead.
     */
    public abstract Set<String> names();

    /**
     * @see {@link #add(CharSequence, Object)}
     */
    public abstract HttpHeaders add(String name, Object value);

    /**
     * Adds a new header with the specified name and value.
     *
     * If the specified value is not a {@link String}, it is converted
     * into a {@link String} by {@link Object#toString()}, except in the cases
     * of {@link Date} and {@link Calendar}, which are formatted to the date
     * format defined in <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1">RFC2616</a>.
     *
     * @param name The name of the header being added
     * @param value The value of the header being added
     *
     * @return {@code this}
     */
    public HttpHeaders add(CharSequence name, Object value) {
        return add(name.toString(), value);
    }

    /**
     * @see {@link #add(CharSequence, Iterable)}
     */
    public abstract HttpHeaders add(String name, Iterable<?> values);

    /**
     * Adds a new header with the specified name and values.
     *
     * This getMethod can be represented approximately as the following code:
     * <pre>
     * for (Object v: values) {
     *     if (v == null) {
     *         break;
     *     }
     *     headers.add(name, v);
     * }
     * </pre>
     *
     * @param name The name of the headers being set
     * @param values The values of the headers being set
     * @return {@code this}
     */
    public HttpHeaders add(CharSequence name, Iterable<?> values) {
        return add(name.toString(), values);
    }

    /**
     * Adds all header entries of the specified {@code headers}.
     *
     * @return {@code this}
     */
    public HttpHeaders add(HttpHeaders headers) {
        if (headers == null) {
            throw new NullPointerException("headers");
        }
        for (Map.Entry<String, String> e: headers) {
            add(e.getKey(), e.getValue());
        }
        return this;
    }

    /**
     * Add the {@code name} to {@code value}.
     * @param name The name to modify
     * @param value The value
     * @return {@code this}
     */
    public abstract HttpHeaders addInt(CharSequence name, int value);

    /**
     * Add the {@code name} to {@code value}.
     * @param name The name to modify
     * @param value The value
     * @return {@code this}
     */
    public abstract HttpHeaders addShort(CharSequence name, short value);

    /**
     * @see {@link #set(CharSequence, Object)}
     */
    public abstract HttpHeaders set(String name, Object value);

    /**
     * Sets a header with the specified name and value.
     *
     * If there is an existing header with the same name, it is removed.
     * If the specified value is not a {@link String}, it is converted into a
     * {@link String} by {@link Object#toString()}, except for {@link Date}
     * and {@link Calendar}, which are formatted to the date format defined in
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1">RFC2616</a>.
     *
     * @param name The name of the header being set
     * @param value The value of the header being set
     * @return {@code this}
     */
    public HttpHeaders set(CharSequence name, Object value) {
        return set(name.toString(), value);
    }

    /**
     * @see {@link #set(CharSequence, Iterable)}
     */
    public abstract HttpHeaders set(String name, Iterable<?> values);

    /**
     * Sets a header with the specified name and values.
     *
     * If there is an existing header with the same name, it is removed.
     * This getMethod can be represented approximately as the following code:
     * <pre>
     * headers.remove(name);
     * for (Object v: values) {
     *     if (v == null) {
     *         break;
     *     }
     *     headers.add(name, v);
     * }
     * </pre>
     *
     * @param name The name of the headers being set
     * @param values The values of the headers being set
     * @return {@code this}
     */
    public HttpHeaders set(CharSequence name, Iterable<?> values) {
        return set(name.toString(), values);
    }

    /**
     * Cleans the current header entries and copies all header entries of the specified {@code headers}.
     *
     * @return {@code this}
     */
    public HttpHeaders set(HttpHeaders headers) {
        checkNotNull(headers, "headers");

        clear();

        if (headers.isEmpty()) {
            return this;
        }

        try {
            headers.forEachEntry(addAllVisitor());
        } catch (Exception e) {
            PlatformDependent.throwException(e);
        }
        return this;
    }

    /**
     * Retains all current headers but calls {@link #set(String, Object)} for each entry in {@code headers}
     *
     * @param headers The headers used to {@link #set(String, Object)} values in this instance
     * @return {@code this}
     */
    public HttpHeaders setAll(HttpHeaders headers) {
        checkNotNull(headers, "headers");

        if (headers.isEmpty()) {
            return this;
        }

        try {
            headers.forEachEntry(setAllVisitor());
        } catch (Exception e) {
            PlatformDependent.throwException(e);
        }
        return this;
    }

    /**
     * Set the {@code name} to {@code value}. This will remove all previous values associated with {@code name}.
     * @param name The name to modify
     * @param value The value
     * @return {@code this}
     */
    public abstract HttpHeaders setInt(CharSequence name, int value);

    /**
     * Set the {@code name} to {@code value}. This will remove all previous values associated with {@code name}.
     * @param name The name to modify
     * @param value The value
     * @return {@code this}
     */
    public abstract HttpHeaders setShort(CharSequence name, short value);

    /**
     * @see {@link #remove(CharSequence)}
     */
    public abstract HttpHeaders remove(String name);

    /**
     * Removes the header with the specified name.
     *
     * @param name The name of the header to remove
     * @return {@code this}
     */
    public HttpHeaders remove(CharSequence name) {
        return remove(name.toString());
    }

    /**
     * Removes all headers from this {@link HttpMessage}.
     *
     * @return {@code this}
     */
    public abstract HttpHeaders clear();

    /**
     * @see {@link #contains(CharSequence, CharSequence, boolean)}
     */
    public boolean contains(String name, String value, boolean ignoreCase) {
        List<String> values = getAll(name);
        if (values.isEmpty()) {
            return false;
        }

        for (String v: values) {
            if (ignoreCase) {
                if (v.equalsIgnoreCase(value)) {
                    return true;
                }
            } else {
                if (v.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns {@code true} if a header with the name and value exists.
     *
     * @param name         the headername
     * @param value        the value
     * @param ignoreCase   {@code true} if case should be ignored
     * @return contains    {@code true} if it contains it {@code false} otherwise
     */
    public boolean contains(CharSequence name, CharSequence value, boolean ignoreCase) {
        return contains(name.toString(), value.toString(), ignoreCase);
    }

    /**
     * Provide a means of iterating over elements in this map with low GC
     *
     * @param visitor The visitor which will visit each element in this map
     * @return The last entry before iteration stopped or {@code null} if iteration went past the end
     */
    public abstract Map.Entry<CharSequence, CharSequence> forEachEntry(EntryVisitor<CharSequence> visitor)
            throws Exception;

    private EntryVisitor<CharSequence> setAllVisitor() {
        return new EntryVisitor<CharSequence>() {
            @Override
            public boolean visit(Entry<CharSequence, CharSequence> entry) {
                set(entry.getKey(), entry.getValue());
                return true;
            }
        };
    }

    private EntryVisitor<CharSequence> addAllVisitor() {
        return new EntryVisitor<CharSequence>() {
            @Override
            public boolean visit(Entry<CharSequence, CharSequence> entry) {
                add(entry.getKey(), entry.getValue());
                return true;
            }
        };
    }

}