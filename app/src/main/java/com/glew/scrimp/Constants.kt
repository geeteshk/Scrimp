package com.glew.scrimp

val SUPPORTED_COUNTRY_CODES = listOf(
    "AE", "AF", "AL", "AM", "CW", "SX", "AO", "AR", "AU", "CC",
    "CX", "HM", "KI", "NF", "NR", "TV", "AW", "AZ", "BA", "BB",
    "BD", "BG", "BH", "BI", "BM", "BN", "BO", "BR", "BS", "BT",
    "BW", "BY", "BZ", "CA", "CD", "CH", "LI", "CL", "CN", "CO",
    "CR", "CU", "CV", "CZ", "DJ", "DK", "FO", "GL", "DO", "DZ",
    "EG", "ER", "ET", "AD", "AT", "AX", "BE", "BL", "CY", "DE",
    "EE", "ES", "FI", "FR", "GF", "GP", "GR", "IE", "IT", "LT",
    "LU", "LV", "MC", "ME", "MF", "MQ", "MT", "NL", "PM", "PT",
    "RE", "SI", "SK", "SM", "TF", "VA", "XK", "YT", "FJ", "FK",
    "GB", "GG", "IM", "JE", "GE", "GH", "GI", "GM", "GN", "GT",
    "GY", "HK", "HN", "HR", "HT", "HU", "ID", "IL", "IN", "IQ",
    "IR", "IS", "JM", "JO", "JP", "KE", "KG", "KH", "KM", "KP",
    "KR", "KW", "KY", "KZ", "LA", "LB", "LK", "LR", "LS", "LY",
    "EH", "MA", "MD", "MG", "MK", "MM", "MN", "MO", "MR", "MU",
    "MV", "MW", "MX", "MY", "MZ", "NA", "NG", "NI", "BV", "NO",
    "SJ", "NP", "CK", "NU", "NZ", "PN", "TK", "OM", "PA", "PE",
    "PG", "PH", "PK", "PL", "PY", "QA", "RO", "RS", "RU", "RW",
    "SA", "SB", "SC", "SD", "SE", "SG", "SH", "SL", "SO", "SR",
    "SS", "ST", "SV", "SY", "SZ", "TH", "TJ", "TM", "TN", "TO",
    "TR", "TT", "TW", "TZ", "UA", "UG", "AS", "BQ", "EC", "FM",
    "GU", "IO", "MH", "MP", "PR", "PW", "TC", "TL", "UM", "US",
    "VG", "VI", "UY", "UZ", "VE", "VN", "VU", "WS", "CF", "CG",
    "CM", "GA", "GQ", "TD", "AG", "AI", "DM", "GD", "KN", "LC",
    "MS", "VC", "BF", "BJ", "CI", "GW", "ML", "NE", "SN", "TG",
    "NC", "PF", "WF", "YE", "ZA", "ZM", "ZW"
)

fun countryFlag(code: String) = code
    .uppercase()
    .split("")
    .filter { it.isNotBlank() }
    .map { it.codePointAt(0) + 0x1F1A5 }
    .joinToString("") { String(Character.toChars(it)) }