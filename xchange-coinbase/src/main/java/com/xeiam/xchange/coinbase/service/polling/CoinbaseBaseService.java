/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.coinbase.service.polling;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.Coinbase;
import com.xeiam.xchange.coinbase.dto.CoinbaseBaseResponse;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseToken;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUser;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseCurrency;
import com.xeiam.xchange.coinbase.service.CoinbaseDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;

/**
 * @author jamespedwards42
 */
abstract class CoinbaseBaseService<T extends Coinbase> extends BasePollingExchangeService {

  protected final T coinbase;
  protected final ParamsDigest signatureCreator;

  protected CoinbaseBaseService(final Class<T> type, final ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    coinbase = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
    signatureCreator = CoinbaseDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  /**
   * Unauthenticated resource that returns currencies supported on Coinbase.
   * 
   * @return A list of currency names and their corresponding ISO code.
   * @throws IOException
   */
  public List<CoinbaseCurrency> getCoinbaseCurrencies() throws IOException {

    return coinbase.getCurrencies();
  }

  /**
   * Unauthenticated resource that creates a user with an email and password.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/users/create.html">coinbase.com/api/doc/1.0/users/create.html</a>
   * @see {@link CoinbaseUser#createNewCoinbaseUser} and {@link CoinbaseUser#createCoinbaseNewUserWithReferrerId}
   * @param user New Coinbase User information.
   * @return Information for the newly created user.
   * @throws IOException
   */
  public CoinbaseUser createCoinbaseUser(final CoinbaseUser user) throws IOException {

    final CoinbaseUser createdUser = coinbase.createUser(user);
    return handleResponse(createdUser);
  }

  /**
   * Unauthenticated resource that creates a user with an email and password.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/users/create.html">coinbase.com/api/doc/1.0/users/create.html</a>
   * @see {@link CoinbaseUser#createNewCoinbaseUser} and {@link CoinbaseUser#createCoinbaseNewUserWithReferrerId}
   * @param user New Coinbase User information.
   * @param oAuthClientId Optional client id that corresponds to your OAuth2 application.
   * @return Information for the newly created user, including information to perform future OAuth requests for the user.
   * @throws IOException
   */
  public CoinbaseUser createCoinbaseUser(final CoinbaseUser user, final String oAuthClientId) throws IOException {

    final CoinbaseUser createdUser = coinbase.createUser(user.withoAuthClientId(oAuthClientId));
    return handleResponse(createdUser);
  }

  /**
   * Creates tokens redeemable for Bitcoin.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/tokens/create.html">coinbase.com/api/doc/1.0/tokens/create.html</a>
   * @return The returned Bitcoin address can be used to send money to the token,
   *         and will be credited to the account of the token redeemer if money is sent both before or after redemption.
   * @throws IOException
   */
  public CoinbaseToken createCoinbaseToken() throws IOException {

    final CoinbaseToken token = coinbase.createToken();
    return handleResponse(token);
  }

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  CurrencyPair.BTC_USD

  );

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }

  protected long getNonce() {

    return System.currentTimeMillis();
  }

  protected <R extends CoinbaseBaseResponse> R handleResponse(final R response) {

    final List<String> errors = response.getErrors();
    if (errors != null && !errors.isEmpty())
      throw new ExchangeException(errors.toString());

    return response;
  }
}
