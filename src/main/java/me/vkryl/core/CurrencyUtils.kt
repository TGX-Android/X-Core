/*
 * This file is a part of X-Core
 * Copyright © Vyacheslav Krylov (slavone@protonmail.ch) 2014-2022
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * File created on 11/03/2017
 */
package me.vkryl.core

import kotlin.math.pow

object CurrencyUtils {

  private const val FLAG_SYMBOL_LEFT = 1
  private const val FLAG_SPACE_BETWEEN = 1 shl 1

  private val MAP = generateMap()
  private val DEFAULT_CURRENCY = Currency(
    symbol = "$",
    shortSymbol = null,
    iconSymbol = null,
    thousandSeparator = ",",
    decimalSeparator = ".",
    exp = 2,
    flags = FLAG_SYMBOL_LEFT
  )

  private fun generateMap(): HashMap<String, Currency> {
    val map = HashMap<String, Currency>()
    map["AED"] = Currency(
      symbol = "AED",
      shortSymbol = "د.إ.‏",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["AFN"] = Currency(
      symbol = "AFN",
      shortSymbol = "؋",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["ALL"] = Currency(
      symbol = "ALL",
      shortSymbol = "Lek",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 2,
      flags = 0
    )
    map["AMD"] = Currency(
      symbol = "AMD",
      shortSymbol = "դր.",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["ARS"] = Currency(
      symbol = "ARS",
      shortSymbol = "$",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["AUD"] = Currency(
      symbol = "AU$",
      shortSymbol = "$",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["AZN"] = Currency(
      symbol = "AZN",
      shortSymbol = "ман.",
      iconSymbol = null,
      thousandSeparator = " ",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["BAM"] = Currency(
      symbol = "BAM",
      shortSymbol = "KM",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["BDT"] = Currency(
      symbol = "BDT",
      shortSymbol = "৳",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["BGN"] = Currency(
      symbol = "BGN",
      shortSymbol = "лв.",
      iconSymbol = null,
      thousandSeparator = " ",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["BND"] = Currency(
      symbol = "BND",
      shortSymbol = "$",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["BOB"] = Currency(
      symbol = "BOB",
      shortSymbol = "Bs",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["BRL"] = Currency(
      symbol = "R$",
      shortSymbol = "R$",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["CAD"] = Currency(
      symbol = "CA$",
      shortSymbol = "$",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["CHF"] = Currency(
      symbol = "CHF",
      shortSymbol = "CHF",
      iconSymbol = null,
      thousandSeparator = "'",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["CLP"] = Currency(
      symbol = "CLP",
      shortSymbol = "$",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 0,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["CNY"] = Currency(
      symbol = "CN¥",
      shortSymbol = "CN¥",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["COP"] = Currency(
      symbol = "COP",
      shortSymbol = "$",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["CRC"] = Currency(
      symbol = "CRC",
      shortSymbol = "₡",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["CZK"] = Currency(
      symbol = "CZK",
      shortSymbol = "Kč",
      iconSymbol = null,
      thousandSeparator = " ",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["DKK"] = Currency(
      symbol = "DKK",
      shortSymbol = "kr",
      iconSymbol = null,
      thousandSeparator = "",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["DOP"] = Currency(
      symbol = "DOP",
      shortSymbol = "$",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["DZD"] = Currency(
      symbol = "DZD",
      shortSymbol = "د.ج.‏",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["EGP"] = Currency(
      symbol = "EGP",
      shortSymbol = "ج.م.‏",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["EUR"] = Currency(
      symbol = "€",
      shortSymbol = "€",
      iconSymbol = null,
      thousandSeparator = " ",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["GBP"] = Currency(
      symbol = "£",
      shortSymbol = "£",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["GEL"] = Currency(
      symbol = "GEL",
      shortSymbol = "GEL",
      iconSymbol = null,
      thousandSeparator = " ",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["GTQ"] = Currency(
      symbol = "GTQ",
      shortSymbol = "Q",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["HKD"] = Currency(
      symbol = "HK$",
      shortSymbol = "$",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["HNL"] = Currency(
      symbol = "HNL",
      shortSymbol = "L",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["HRK"] = Currency(
      symbol = "HRK",
      shortSymbol = "kn",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["HUF"] = Currency(
      symbol = "HUF",
      shortSymbol = "Ft",
      iconSymbol = null,
      thousandSeparator = " ",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["IDR"] = Currency(
      symbol = "IDR",
      shortSymbol = "Rp",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["ILS"] = Currency(
      symbol = "₪",
      shortSymbol = "₪",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["INR"] = Currency(
      symbol = "₹",
      shortSymbol = "₹",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["ISK"] = Currency(
      symbol = "ISK",
      shortSymbol = "kr",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 0,
      flags = FLAG_SPACE_BETWEEN
    )
    map["JMD"] = Currency(
      symbol = "JMD",
      shortSymbol = "$",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["JPY"] = Currency(
      symbol = "¥",
      shortSymbol = "￥",
      iconSymbol = "¥",
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 0,
      flags = FLAG_SYMBOL_LEFT
    )
    map["KES"] = Currency(
      symbol = "KES",
      shortSymbol = "Ksh",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["KGS"] = Currency(
      symbol = "KGS",
      shortSymbol = "KGS",
      iconSymbol = null,
      thousandSeparator = " ",
      decimalSeparator = "-",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["KRW"] = Currency(
      symbol = "₩",
      shortSymbol = "₩",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 0,
      flags = FLAG_SYMBOL_LEFT
    )
    map["KZT"] = Currency(
      symbol = "KZT",
      shortSymbol = "₸",
      iconSymbol = null,
      thousandSeparator = " ",
      decimalSeparator = "-",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["LBP"] = Currency(
      symbol = "LBP",
      shortSymbol = "ل.ل.‏",
      iconSymbol = "£",
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["LKR"] = Currency(
      symbol = "LKR",
      shortSymbol = "රු.",
      iconSymbol = "₨",
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["MAD"] = Currency(
      symbol = "MAD",
      shortSymbol = "د.م.‏",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["MDL"] = Currency(
      symbol = "MDL",
      shortSymbol = "MDL",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["MNT"] = Currency(
      symbol = "MNT",
      shortSymbol = "MNT",
      iconSymbol = null,
      thousandSeparator = " ",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["MUR"] = Currency(
      symbol = "MUR",
      shortSymbol = "MUR",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["MVR"] = Currency(
      symbol = "MVR",
      shortSymbol = "MVR",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["MXN"] = Currency(
      symbol = "MX$",
      shortSymbol = "$",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["MYR"] = Currency(
      symbol = "MYR",
      shortSymbol = "RM",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["MZN"] = Currency(
      symbol = "MZN",
      shortSymbol = "MTn",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["NGN"] = Currency(
      symbol = "NGN",
      shortSymbol = "₦",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["NIO"] = Currency(
      symbol = "NIO",
      shortSymbol = "C$",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["NOK"] = Currency(
      symbol = "NOK",
      shortSymbol = "kr",
      iconSymbol = null,
      thousandSeparator = " ",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["NPR"] = Currency(
      symbol = "NPR",
      shortSymbol = "नेरू",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["NZD"] = Currency(
      symbol = "NZ$",
      shortSymbol = "$",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["PAB"] = Currency(
      symbol = "PAB",
      shortSymbol = "B/.",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["PEN"] = Currency(
      symbol = "PEN",
      shortSymbol = "S/.",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["PHP"] = Currency(
      symbol = "PHP",
      shortSymbol = "₱",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["PKR"] = Currency(
      symbol = "PKR",
      shortSymbol = "₨",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["PLN"] = Currency(
      symbol = "PLN",
      shortSymbol = "zł",
      iconSymbol = null,
      thousandSeparator = " ",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["PYG"] = Currency(
      symbol = "PYG",
      shortSymbol = "₲",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 0,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["QAR"] = Currency(
      symbol = "QAR",
      shortSymbol = "ر.ق.‏",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["RON"] = Currency(
      symbol = "RON",
      shortSymbol = "RON",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["RSD"] = Currency(
      symbol = "RSD",
      shortSymbol = "дин.",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["RUB"] = Currency(
      symbol = "RUB",
      shortSymbol = "₽",
      iconSymbol = null,
      thousandSeparator = " ",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["SAR"] = Currency(
      symbol = "SAR",
      shortSymbol = "ر.س.‏",
      iconSymbol = "﷼",
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["SEK"] = Currency(
      symbol = "SEK",
      shortSymbol = "kr",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["SGD"] = Currency(
      symbol = "SGD",
      shortSymbol = "$",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["THB"] = Currency(
      symbol = "฿",
      shortSymbol = "฿",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["TJS"] = Currency(
      symbol = "TJS",
      shortSymbol = "TJS",
      iconSymbol = null,
      thousandSeparator = " ",
      decimalSeparator = ";",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["TRY"] = Currency(
      symbol = "TRY",
      shortSymbol = "TL",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["TTD"] = Currency(
      symbol = "TTD",
      shortSymbol = "$",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["TWD"] = Currency(
      symbol = "NT$",
      shortSymbol = "NT$",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["TZS"] = Currency(
      symbol = "TZS",
      shortSymbol = "TSh",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["UAH"] = Currency(
      symbol = "UAH",
      shortSymbol = "₴",
      iconSymbol = null,
      thousandSeparator = " ",
      decimalSeparator = ",",
      exp = 2,
      flags = 0
    )
    map["UGX"] = Currency(
      symbol = "UGX",
      shortSymbol = "USh",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 0,
      flags = FLAG_SYMBOL_LEFT
    )
    map["USD"] = Currency(
      symbol = "$",
      shortSymbol = "$",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT
    )
    map["UYU"] = Currency(
      symbol = "UYU",
      shortSymbol = "$",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["UZS"] = Currency(
      symbol = "UZS",
      shortSymbol = "UZS",
      iconSymbol = null,
      thousandSeparator = " ",
      decimalSeparator = ",",
      exp = 2,
      flags = FLAG_SPACE_BETWEEN
    )
    map["VND"] = Currency(
      symbol = "₫",
      shortSymbol = "₫",
      iconSymbol = null,
      thousandSeparator = ".",
      decimalSeparator = ",",
      exp = 0,
      flags = FLAG_SPACE_BETWEEN
    )
    map["YER"] = Currency(
      symbol = "YER",
      shortSymbol = "ر.ي.‏",
      iconSymbol = "﷼",
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    map["ZAR"] = Currency(
      symbol = "ZAR",
      shortSymbol = "R",
      iconSymbol = null,
      thousandSeparator = ",",
      decimalSeparator = ".",
      exp = 2,
      flags = FLAG_SYMBOL_LEFT or FLAG_SPACE_BETWEEN
    )
    return map
  }

  @JvmStatic
  fun getCurrency(currency: String): Currency {
    if (!isEmpty(currency)) {
      val data = MAP[currency]
      if (data != null) {
        return data
      }
    }
    return DEFAULT_CURRENCY
  }

  @JvmStatic
  fun getCurrencyChar(currency: String): String? {
    if (isEmpty(currency)) {
      return currency
    }
    val data = getCurrency(currency)
    return if (isEmpty(data.iconSymbol)) data.shortSymbol else data.iconSymbol
    /*switch (currency) {
  case "AUD": return "$";
  case "AZN": return "₼";
  case "BSD": return "$";
  case "BBD": return "$";
  case "BYN": return "Br";
  case "BZD": return "BZ$";
  case "BMD": return "$";
  case "BOB": return "$b";
  case "BAM": return "KM";
  case "BWP": return "P";
  case "BGN": return "лв";
  case "BRL": return "R$";
  case "BND": return "$";
  case "KHR": return "៛";
  case "CAD": return "$";
  case "KYD": return "$";
  case "CLP": return "$";
  case "CNY": return "¥";
  case "COP": return "$";
  case "CRC": return "₡";
  case "HRK": return "kn";
  case "CUP": return "₱";
  case "CZK": return "Kč";
  case "DKK": return "kr";
  case "DOP": return "RD$";
  case "XCD": return "$";
  case "EGP": return "£";
  case "SVC": return "$";
  case "EUR": return "€";
  case "FKP": return "£";
  case "FJD": return "$";
  case "GHS": return "¢";
  case "GIP": return "£";
  case "GTQ": return "Q";
  case "GGP": return "£";
  case "GYD": return "$";
  case "HNL": return "L";
  case "HKD": return "$";
  case "HUF": return "Ft";
  case "ISK": return "kr";
  case "IDR": return "Rp";
  case "IRR": return "﷼";
  case "IMP": return "£";
  case "ILS": return "₪";
  case "JMD": return "J$";
  case "JPY": return "¥";
  case "JEP": return "£";
  case "KZT": return "лв";
  case "KPW": return "₩";
  case "KRW": return "₩";
  case "KGS": return "лв";
  case "LAK": return "₭";
  case "LBP": return "£";
  case "LRD": return "$";
  case "MKD": return "ден";
  case "MYR": return "RM";
  case "MUR": return "₨";
  case "MXN": return "$";
  case "MNT": return "₮";
  case "MZN": return "MT";
  case "NAD": return "$";
  case "NPR": return "₨";
  case "ANG": return "ƒ";
  case "NZD": return "$";
  case "NIO": return "C$";
  case "NGN": return "₦";
  case "NOK": return "kr";
  case "OMR": return "﷼";
  case "PKR": return "₨";
  case "PAB": return "B/.";
  case "PYG": return "Gs";
  case "PEN": return "S/.";
  case "PHP": return "₱";
  case "PLN": return "zł";
  case "QAR": return "﷼";
  case "RON": return "lei";
  case "RUB": return "₽";
  case "SHP": return "£";
  case "SAR": return "﷼";
  case "RSD": return "Дин.";
  case "SCR": return "₨";
  case "SGD": return "$";
  case "SBD": return "$";
  case "SOS": return "S";
  case "ZAR": return "R";
  case "LKR": return "₨";
  case "SEK": return "kr";
  case "CHF": return "CHF";
  case "SRD": return "$";
  case "SYP": return "£";
  case "TWD": return "NT$";
  case "THB": return "฿";
  case "TTD": return "TT$";
  case "TVD": return "$";
  case "UAH": return "₴";
  case "GBP": return "£";
  case "USD": return "$";
  case "UYU": return "$U";
  case "UZS": return "лв";
  case "VEF": return "Bs";
  case "VND": return "₫";
  case "YER": return "﷼";
  case "ZWD": return "Z$";
}*/
  }

  @JvmStatic
  fun buildAmount(currency: String, amount: Long): String {
    var amount = amount
    val data = getCurrency(currency)
    val b = StringBuilder()
    val symbol = if (isEmpty(data.iconSymbol)) data.shortSymbol else data.iconSymbol
    if (data.flags and FLAG_SYMBOL_LEFT != 0) {
      b.append(symbol)
      if (data.flags and FLAG_SPACE_BETWEEN != 0) {
        b.append(' ')
      }
    }
    var decimal: Long = -1
    var exp: Long = -1
    if (data.exp != 0) {
      exp = 10.0.pow(data.exp.toDouble()).toLong()
      decimal = amount % exp
      amount /= exp
    }
    if (amount == 0L) {
      b.append('0')
    } else {
      var i = b.length
      var first = true
      while (amount > 0) {
        val remain = amount % 1000
        if (first) {
          first = false
        } else {
          b.insert(i, data.thousandSeparator)
        }
        amount /= 1000
        b.insert(i, remain)
        if (amount > 0 && remain < 100) {
          b.insert(i, '0')
          if (remain < 10) {
            b.insert(i, '0')
          }
        }
      }
      if (decimal != -1L && decimal != 0L) {
        b.append(data.decimalSeparator)
        i = b.length
        b.append(decimal)
        exp /= 10
        while (10.let { exp /= it; exp } != 0L) {
          if (decimal < exp) {
            b.insert(i, '0')
          }
        }
      }
    }
    if (data.flags and FLAG_SYMBOL_LEFT == 0) {
      if (data.flags and FLAG_SPACE_BETWEEN != 0) {
        b.append(' ')
      }
      b.append(symbol)
    }
    return b.toString()
  }

  data class Currency(
    val symbol: String,
    val shortSymbol: String?,
    val iconSymbol: String?,
    val thousandSeparator: String,
    val decimalSeparator: String,
    val exp: Int,
    val flags: Int
  )
}