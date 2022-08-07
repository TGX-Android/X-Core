/*
 * Copyright 2011 Fabian Barney
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.vkryl.core.unit

import kotlin.math.pow

/**
 * @author Fabian Barney
 */
enum class ByteUnit {
  /** <pre>
   * Byte (B)
   * 1 Byte
  </pre> */
  BYTE {
    override fun toBytes(size: Double): Long {
      return size.toLong()
    }

    override fun convert(size: Double, unit: ByteUnit): Double {
      return unit.toBytes(size).toDouble()
    }
  },

  /** <pre>
   * Kibibyte (KiB)
   * 2^10 Byte = 1.024 Byte
  </pre> */
  KIB {
    override fun toBytes(size: Double): Long {
      return safeMulti(size, C_KIB).toLong()
    }

    override fun convert(size: Double, unit: ByteUnit): Double {
      return unit.toKiB(size)
    }
  },

  /** <pre>
   * Mebibyte (MiB)
   * 2^20 Byte = 1.024 * 1.024 Byte = 1.048.576 Byte
  </pre> */
  MIB {
    override fun toBytes(size: Double): Long {
      return safeMulti(size, C_MIB).toLong()
    }

    override fun convert(size: Double, unit: ByteUnit): Double {
      return unit.toMiB(size)
    }
  },

  /** <pre>
   * Gibibyte (GiB)
   * 2^30 Byte = 1.024 * 1.024 * 1.024 Byte = 1.073.741.824 Byte
  </pre> */
  GIB {
    override fun toBytes(size: Double): Long {
      return safeMulti(size, C_GIB).toLong()
    }

    override fun convert(size: Double, unit: ByteUnit): Double {
      return unit.toGiB(size)
    }
  },

  /** <pre>
   * Tebibyte (TiB)
   * 2^40 Byte = 1.024 * 1.024 * 1.024 * 1.024 Byte = 1.099.511.627.776 Byte
  </pre> */
  TIB {
    override fun toBytes(size: Double): Long {
      return safeMulti(size, C_TIB).toLong()
    }

    override fun convert(size: Double, unit: ByteUnit): Double {
      return unit.toTiB(size)
    }
  },

  /** <pre>
   * Pebibyte (PiB)
   * 2^50 Byte = 1.024 * 1.024 * 1.024 * 1.024 * 1.024 Byte = 1.125.899.906.842.624 Byte
  </pre> */
  PIB {
    override fun toBytes(size: Double): Long {
      return safeMulti(size, C_PIB).toLong()
    }

    override fun convert(size: Double, unit: ByteUnit): Double {
      return unit.toPiB(size)
    }
  },

  /** <pre>
   * Kilobyte (kB)
   * 10^3 Byte = 1.000 Byte
  </pre> */
  KB {
    override fun toBytes(size: Double): Long {
      return safeMulti(size, C_KB).toLong()
    }

    override fun convert(size: Double, unit: ByteUnit): Double {
      return unit.toKB(size)
    }
  },

  /** <pre>
   * Megabyte (MB)
   * 10^6 Byte = 1.000.000 Byte
  </pre> */
  MB {
    override fun toBytes(size: Double): Long {
      return safeMulti(size, C_MB).toLong()
    }

    override fun convert(size: Double, unit: ByteUnit): Double {
      return unit.toMB(size)
    }
  },

  /** <pre>
   * Gigabyte (GB)
   * 10^9 Byte = 1.000.000.000 Byte
  </pre> */
  GB {
    override fun toBytes(size: Double): Long {
      return safeMulti(size, C_GB).toLong()
    }

    override fun convert(size: Double, unit: ByteUnit): Double {
      return unit.toGB(size)
    }
  },

  /** <pre>
   * Terabyte (TB)
   * 10^12 Byte = 1.000.000.000.000 Byte
  </pre> */
  TB {
    override fun toBytes(size: Double): Long {
      return safeMulti(size, C_TB).toLong()
    }

    override fun convert(size: Double, unit: ByteUnit): Double {
      return unit.toTB(size)
    }
  },

  /** <pre>
   * Petabyte (PB)
   * 10^15 Byte = 1.000.000.000.000.000 Byte
  </pre> */
  PB {
    override fun toBytes(size: Double): Long {
      return safeMulti(size, C_PB).toLong()
    }

    override fun convert(size: Double, unit: ByteUnit): Double {
      return unit.toPB(size)
    }
  };

  abstract fun toBytes(size: Double): Long
  abstract fun convert(size: Double, unit: ByteUnit): Double

  fun toKiB(d: Double): Double {
    return toBytes(d) / C_KIB
  }

  fun toMiB(d: Double): Double {
    return toBytes(d) / C_MIB
  }

  fun toGiB(d: Double): Double {
    return toBytes(d) / C_GIB
  }

  fun toTiB(d: Double): Double {
    return toBytes(d) / C_TIB
  }

  fun toPiB(d: Double): Double {
    return toBytes(d) / C_PIB
  }

  fun toKB(d: Double): Double {
    return toBytes(d) / C_KB
  }

  fun toMB(d: Double): Double {
    return toBytes(d) / C_MB
  }

  fun toGB(d: Double): Double {
    return toBytes(d) / C_GB
  }

  fun toTB(d: Double): Double {
    return toBytes(d) / C_TB
  }

  fun toPB(d: Double): Double {
    return toBytes(d) / C_PB
  }

  @JvmOverloads
  fun convert(size: Double, unit: BitUnit, wordSize: Int = java.lang.Byte.SIZE): Double {
    val bytes = unit.toBits(size) / wordSize
    return convert(bytes, BYTE)
  }

  /**
   * Komfort-Methoden für Cross-Konvertierung
   */
  fun toBits(size: Double): Double {
    return BitUnit.BIT.convert(size, this)
  }

  fun toBits(size: Double, wordSize: Int): Double {
    return BitUnit.BIT.convert(size, this, wordSize)
  }

  fun toKibit(size: Double): Double {
    return BitUnit.KIBIT.convert(size, this)
  }

  fun toMibit(size: Double): Double {
    return BitUnit.MIBIT.convert(size, this)
  }

  fun toGibit(size: Double): Double {
    return BitUnit.GIBIT.convert(size, this)
  }

  fun toTibit(size: Double): Double {
    return BitUnit.TIBIT.convert(size, this)
  }

  fun toPibit(size: Double): Double {
    return BitUnit.PIBIT.convert(size, this)
  }

  fun toKibit(size: Double, wordSize: Int): Double {
    return BitUnit.KIBIT.convert(size, this, wordSize)
  }

  fun toMibit(size: Double, wordSize: Int): Double {
    return BitUnit.MIBIT.convert(size, this, wordSize)
  }

  fun toGibit(size: Double, wordSize: Int): Double {
    return BitUnit.GIBIT.convert(size, this, wordSize)
  }

  fun toTibit(size: Double, wordSize: Int): Double {
    return BitUnit.TIBIT.convert(size, this, wordSize)
  }

  fun toPibit(size: Double, wordSize: Int): Double {
    return BitUnit.PIBIT.convert(size, this, wordSize)
  }

  fun toKbit(size: Double): Double {
    return BitUnit.KBIT.convert(size, this)
  }

  fun toMbit(size: Double): Double {
    return BitUnit.MBIT.convert(size, this)
  }

  fun toGbit(size: Double): Double {
    return BitUnit.GBIT.convert(size, this)
  }

  fun toTbit(size: Double): Double {
    return BitUnit.TBIT.convert(size, this)
  }

  fun toPbit(size: Double): Double {
    return BitUnit.PBIT.convert(size, this)
  }

  fun toKbit(size: Double, wordSize: Int): Double {
    return BitUnit.KBIT.convert(size, this, wordSize)
  }

  fun toMbit(size: Double, wordSize: Int): Double {
    return BitUnit.MBIT.convert(size, this, wordSize)
  }

  fun toGbit(size: Double, wordSize: Int): Double {
    return BitUnit.GBIT.convert(size, this, wordSize)
  }

  fun toTbit(size: Double, wordSize: Int): Double {
    return BitUnit.TBIT.convert(size, this, wordSize)
  }

  fun toPbit(size: Double, wordSize: Int): Double {
    return BitUnit.PBIT.convert(size, this, wordSize)
  }

  companion object {

    val C_KIB = 2.0.pow(10.0)
    val C_MIB = 2.0.pow(20.0)
    val C_GIB = 2.0.pow(30.0)
    val C_TIB = 2.0.pow(40.0)
    val C_PIB = 2.0.pow(50.0)
    val C_KB = 10.0.pow(3.0)
    val C_MB = 10.0.pow(6.0)
    val C_GB = 10.0.pow(9.0)
    val C_TB = 10.0.pow(12.0)
    val C_PB = 10.0.pow(15.0)

    private const val MAX = Double.MAX_VALUE

    fun safeMulti(d: Double, multi: Double): Double {
      val limit = MAX / multi
      if (d > limit) {
        return Double.MAX_VALUE
      }
      return if (d < -limit) {
        Double.MIN_VALUE
      } else d * multi
    }
  }
}