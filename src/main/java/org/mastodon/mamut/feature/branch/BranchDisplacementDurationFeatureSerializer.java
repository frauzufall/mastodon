/*-
 * #%L
 * Mastodon
 * %%
 * Copyright (C) 2014 - 2021 Tobias Pietzsch, Jean-Yves Tinevez
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
package org.mastodon.mamut.feature.branch;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.mastodon.feature.io.FeatureSerializer;
import org.mastodon.io.FileIdToObjectMap;
import org.mastodon.io.ObjectToFileIdMap;
import org.mastodon.io.properties.DoublePropertyMapSerializer;
import org.mastodon.mamut.feature.branch.BranchDisplacementDurationFeature.Spec;
import org.mastodon.mamut.model.Link;
import org.mastodon.mamut.model.ModelGraph;
import org.mastodon.mamut.model.branch.BranchLink;
import org.mastodon.mamut.model.branch.ModelBranchGraph;
import org.mastodon.properties.DoublePropertyMap;
import org.scijava.plugin.Plugin;

@Plugin( type = FeatureSerializer.class )
public class BranchDisplacementDurationFeatureSerializer implements BranchFeatureSerializer< BranchDisplacementDurationFeature, BranchLink, Link >
{

	@Override
	public Spec getFeatureSpec()
	{
		return BranchDisplacementDurationFeature.SPEC;
	}

	@Override
	public BranchDisplacementDurationFeature deserialize(
			final FileIdToObjectMap< Link > idmap,
			final ObjectInputStream ois,
			final ModelBranchGraph branchGraph,
			final ModelGraph graph ) throws ClassNotFoundException, IOException
	{
		// Read the map link -> val.
		final DoublePropertyMap< Link > dispLMap = new DoublePropertyMap<>( graph.edges(), Double.NaN );
		final DoublePropertyMap< Link > durLMap = new DoublePropertyMap<>( graph.edges(), Double.NaN );
		final DoublePropertyMapSerializer< Link > dispPms = new DoublePropertyMapSerializer<>( dispLMap );
		final DoublePropertyMapSerializer< Link > durPms = new DoublePropertyMapSerializer<>( durLMap );
		final String lengthUnits = ois.readUTF();
		dispPms.readPropertyMap( idmap, ois );
		durPms.readPropertyMap( idmap, ois );

		// Map to branch-link -> val.
		return new BranchDisplacementDurationFeature(
				BranchFeatureSerializer.mapToBranchLinkMap( dispLMap, branchGraph ),
				BranchFeatureSerializer.mapToBranchLinkMap( durLMap, branchGraph ),
				lengthUnits );
	}

	@Override
	public void serialize(
			final BranchDisplacementDurationFeature feature,
			final ObjectToFileIdMap< Link > idmap,
			final ObjectOutputStream oos,
			final ModelBranchGraph branchGraph,
			final ModelGraph graph ) throws IOException
	{
		final DoublePropertyMap< Link > dispLMap = BranchFeatureSerializer.branchLinkMapToMap( feature.dispMap, branchGraph, graph );
		final DoublePropertyMap< Link > durLMap = BranchFeatureSerializer.branchLinkMapToMap( feature.durMap, branchGraph, graph );
		final DoublePropertyMapSerializer< Link > dispPms = new DoublePropertyMapSerializer<>( dispLMap );
		final DoublePropertyMapSerializer< Link > durPms = new DoublePropertyMapSerializer<>( durLMap );
		oos.writeUTF( feature.lengthUnits );
		dispPms.writePropertyMap( idmap, oos );
		durPms.writePropertyMap( idmap, oos );
	}
}