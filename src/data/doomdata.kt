package data


// Emacs style mode select   -*- C++ -*-
//-----------------------------------------------------------------------------
//
// $Id: doomdata.java,v 1.4 2011/02/11 00:11:13 velktron Exp $
//
// Copyright (C) 1993-1996 by id Software, Inc.
// Copyright (C) 2022 hiperbou
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// DESCRIPTION:
//  all external data is defined here
//  most of the data is loaded into different structures at run time
//  some internal structures shared by many modules are here
//
//-----------------------------------------------------------------------------
// The most basic types we use, portability.
//#include "doomtype.h"
// Some global defines, that configure the game.
object doomdata {
    //
    // LineDef attributes.
    //
    // Solid, is an obstacle.
    const val ML_BLOCKING = 1

    // Blocks monsters only.
    const val ML_BLOCKMONSTERS = 2

    // Backside will not be present at all
    //  if not two sided.
    const val ML_TWOSIDED = 4

    // If a texture is pegged, the texture will have
    // the end exposed to air held constant at the
    // top or bottom of the texture (stairs or pulled
    // down things) and will move with a height change
    // of one of the neighbor sectors.
    // Unpegged textures allways have the first row of
    // the texture at the top pixel of the line for both
    // top and bottom textures (use next to windows).
    // upper texture unpegged
    const val ML_DONTPEGTOP = 8

    // lower texture unpegged
    const val ML_DONTPEGBOTTOM = 16

    // In AutoMap: don't map as two sided: IT'S A SECRET!
    const val ML_SECRET = 32

    // Sound rendering: don't let sound cross two of these.
    const val ML_SOUNDBLOCK = 64

    // Don't draw on the automap at all.
    const val ML_DONTDRAW = 128

    // Set if already seen, thus drawn in automap.
    const val ML_MAPPED = 256

    // BSP node structure.
    // Indicate a leaf.
    var NF_SUBSECTOR = 0x8000

    //
    // Map level types.
    // The following data structures define the persistent format
    // used in the lumps of the WAD files.
    //
    // Lump order in a map WAD: each map needs a couple of lumps
    // to provide a complete scene geometry description.
    // Maes: this was defined as a typeless enum, probably intended to be used as cardinality?
    // Turning it into "ML" enum or int consts.
    enum class ML {
        ML_LABEL,  // A separator, name, ExMx or MAPxx
        ML_THINGS,  // Monsters, items..
        ML_LINEDEFS,  // LineDefs, from editing
        ML_SIDEDEFS,  // SideDefs, from editing
        ML_VERTEXES,  // Vertices, edited and BSP splits generated
        ML_SEGS,  // LineSegs, from LineDefs split by BSP
        ML_SSECTORS,  // SubSectors, list of LineSegs
        ML_NODES,  // BSP nodes
        ML_SECTORS,  // Sectors, from editing
        ML_REJECT,  // LUT, sector-sector visibility	
        ML_BLOCKMAP // LUT, motion clipping, walls/grid element
    }

    // A single Vertex.
    /*internal class mapvertex_t {
        var x: Short = 0
        var y: Short = 0
    }*/

    // A SideDef, defining the visual appearance of a wall,
    // by setting textures and offsets.
    internal class mapsidedef_t {
        var textureoffset: Short = 0
        var rowoffset: Short = 0
        var toptexture = CharArray(8)
        var bottomtexture = CharArray(8)
        var midtexture = CharArray(8)

        // Front sector, towards viewer.
        var sector: Short = 0
    }

    // A LineDef, as used for editing, and as input
    // to the BSP builder.
    internal class maplinedef_t {
        var v1: Short = 0
        var v2: Short = 0
        var flags: Short = 0
        var special: Short = 0
        var tag: Short = 0

        // sidenum[1] will be -1 if one sided
        var sidenum = IntArray(2)
    }

    // Sector definition, from editing.
    internal class mapsector_t {
        var floorheight: Short = 0
        var ceilingheight: Short = 0
        var floorpic = CharArray(8)
        var ceilingpic = CharArray(8)
        var lightlevel: Short = 0
        var special: Short = 0
        var tag: Short = 0
    }

    // SubSector, as generated by BSP.
    internal class mapsubsector_t {
        var numsegs: Short = 0

        // Index of first one, segs are stored sequentially.
        var firstseg: Short = 0
    }

    // LineSeg, generated by splitting LineDefs
    // using partition lines selected by BSP builder.
    internal class mapseg_t {
        var v1: Short = 0
        var v2: Short = 0
        var angle: Short = 0
        var linedef: Short = 0
        var side: Short = 0
        var offset: Short = 0
    }

    internal class mapnode_t {
        // Partition line from (x,y) to x+dx,y+dy)
        var x: Short = 0
        var y: Short = 0
        var dx: Short = 0
        var dy: Short = 0

        // Bounding box for each child,
        // clip against view frustum.
        var bbox = Array(2) { ShortArray(4) }

        // If NF_SUBSECTOR its a subsector,
        // else it's a node of another subtree.
        //Maes: used to be unsigned short
        var children = IntArray(2)
    }
}