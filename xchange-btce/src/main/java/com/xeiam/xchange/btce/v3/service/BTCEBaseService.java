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
package com.xeiam.xchange.btce.v3.service;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;

/**
 * @author Matija Mazi
 */
public class BTCEBaseService extends BaseExchangeService {

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  CurrencyPair.BTC_USD,

  CurrencyPair.BTC_RUR,

  CurrencyPair.BTC_EUR,

  CurrencyPair.LTC_BTC,

  CurrencyPair.LTC_USD,

  CurrencyPair.LTC_RUR,

  CurrencyPair.LTC_EUR,

  CurrencyPair.NMC_BTC,

  CurrencyPair.NMC_USD,

  CurrencyPair.USD_RUR,

  CurrencyPair.EUR_USD,

  CurrencyPair.EUR_RUR,

  CurrencyPair.NVC_BTC,

  CurrencyPair.NVC_USD,

  CurrencyPair.TRC_BTC,

  CurrencyPair.PPC_BTC,

  CurrencyPair.PPC_USD,

  CurrencyPair.FTC_BTC,

  CurrencyPair.XPM_BTC

  );

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCEBaseService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }
}
