package st

import doom.SourceCode.ST_Stuff
import doom.event_t

// Emacs style mode select   -*- C++ -*- 
//-----------------------------------------------------------------------------
//
// $Id: IDoomStatusBar.java,v 1.4 2012/09/24 17:16:23 velktron Exp $
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
//	Status bar code.
//	Does the face/direction indicator animatin.
//	Does palette indicators as well (red pain/berserk, bright pickup)
//
//-----------------------------------------------------------------------------
interface IDoomStatusBar {
    //
    // STATUS BAR
    //
    fun NotifyAMEnter()
    fun NotifyAMExit()

    /** Called by main loop.  */
    @ST_Stuff.C(ST_Stuff.ST_Responder)
    fun Responder(ev: event_t): Boolean

    /** Called by main loop.  */
    fun Ticker()

    /** Called by main loop. */
    fun Drawer(fullscreen: Boolean, refresh: Boolean)

    /** Called when the console player is spawned on each level.  */
    fun Start()

    /** Called by startup code.  */
    fun Init()

    /** Used externally to determine window scaling.
     * This means that drawing transparent status bars is possible, but
     * it will look fugly because of the solid windowing (and possibly
     * HOMS).
     */
    fun getHeight(): Int

    /** Forces a full refresh for reasons not handled otherwise, e.g. after full-page
     * draws of help screens, which normally don't trigger a complete redraw even if
     * they should, really.
     */
    fun forceRefresh()
}