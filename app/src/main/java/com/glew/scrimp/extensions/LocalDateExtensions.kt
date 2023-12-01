package com.glew.scrimp.extensions

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

const val DEFAULT_DATE_PATTERN = "MM/dd/yyyy"
const val AXIS_LABEL_PATTERN = "MM/dd"
const val DEFAULT_TIME_PATTERN = "hh:mm a"

fun LocalDate.defaultFormat(): String = this.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN))

fun LocalDate.axisLabelFormat(): String = this.format(DateTimeFormatter.ofPattern(AXIS_LABEL_PATTERN))

fun LocalTime.defaultFormat(): String = this.format(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN))