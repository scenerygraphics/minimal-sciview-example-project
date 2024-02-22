/*-
 * #%L
 * Scenery-backed 3D visualization package for ImageJ.
 * %%
 * Copyright (C) 2016 - 2018 SciView developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package sc.iview.minimal;

import graphics.scenery.Sphere;
import graphics.scenery.primitives.TextBoard;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.scijava.command.Command;
import org.scijava.command.CommandService;
import org.scijava.plugin.Menu;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import sc.iview.SciView;

import static sc.iview.commands.MenuWeights.DEMO;

/**
 * Make a sphere and say hello.
 *
 * The annotations in the next line define where this plugin will appear in sciview's menu, and under which label.
 *
 * @author Kyle Harrington
 */
@Plugin(type = Command.class, label = "My Demo", menuRoot = "SciView", //
        menu = { @Menu(label = "Demo", weight = DEMO), //
                 @Menu(label = "My Demo") })
public class MyDemo implements Command {

    // This parameter is necessary to be able to access sciview from this plugin. It is automatically assigned
    // by the scijava plugin infrastructure, so just declaring it is sufficient.
    @Parameter
    private SciView sciView;

    /**
     * This is the function that is called when the plugin is run either from the menu, or via the CommandService, as
     * we're doing further down in the main function.
     */
    @Override
    public void run() {
        // First, we create a text board with the default font.
        final TextBoard board = new TextBoard("SourceSansPro-Regular.ttf", false);
        board.setText("hi from sciview");
        board.setTransparent(0);
        // As sciview renders in HDR, the background color is a very bright white.
        board.setBackgroundColor(new Vector4f(10.0f));
        // The color of the text itself will be black.
        board.setFontColor(new Vector4f(0.0f, 0.0f, 0.0f, 1.0f));
        // We scale down the text board a little bit so it's not too large.
        board.spatial().setScale(new Vector3f(0.5f));
        // And finally, we add it to sciview's scene.
        sciView.addNode(board);

        // In addition, we create a sphere directly via sciview. This automatically adds the sphere to the scene and
        // makes the sphere the active node in the scene.
        final Sphere sphere = sciView.addSphere();
        // We move the sphere a little bit to the left. 1.0 corresponds to 1m in real world units.
        // In sciview's and scenery's coordinate system, you are by default looking down the negative Z axis, while
        // X goes to the right, and Y to the top. The coordinates given here means we are moving the sphere 1m to
        // the left.
        sphere.spatial().setPosition(new Vector3f(-1.0f, 0.0f, 0.0f));
        // Finally, we center the camera on the active node, which is the sphere we've just set up.
        sciView.centerOnNode( sciView.getActiveNode() );
    }

    public static void main(final String... args) throws Exception {
        // We have to make SciView first to set up the graphics properly.
		SciView sv = SciView.create();
        // This line then runs our MyDemo command via scijava's CommandService.
        sv.getScijavaContext().getService(CommandService.class).run(MyDemo.class, true);
	}
}
