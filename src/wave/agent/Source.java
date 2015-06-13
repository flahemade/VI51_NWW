package wave.agent;

import java.util.UUID;

import wave.Environment.Environment;
import wave.behavior.GenerateInfluence;
import wave.behavior.KillInfluence;
import wave.behavior.RestInfluence;
import wave.body.AgentBody;
import wave.body.SourceBody;
import wave.body.WaveBody;
import fr.utbm.info.vi51.framework.math.Point2f;

public class Source extends Agent {

	private SourceBody body; 
	
	private UUID mother = null; //the UUID of wave who create source by hit a wall
	
	private boolean active;
	
	private float lastHitTime;
	
	private int nbHit;
	
	private int beginRadius = 1; //1 if we create a source, the radius of mother wave if its wall contact
	//create a normal source
	public Source(float freq, float amp, Point2f pos){
		this.body = new SourceBody(freq,amp,pos);
		this.active = true;
		this.nbHit = 0;
	}
	//create an image source, if it's create with wall contact
	public Source(WaveBody mother, Point2f contactPoint,int begin_Radius){
		this.setMother(mother.getID());
		this.body = new SourceBody(mother.getFrequency(),mother.getAmplitude()/2,contactPoint);
		this.active = true;
		this.nbHit = 0;
		this.beginRadius = begin_Radius;
	}
	/*
	 * decide() change the influence of the body given its body properties.
	 * 
	 * The source generate one wave
	 * Else if active is false it kill itslef
	 * Else nothing
	 */
	@Override
	public boolean decide(float currentTime, Environment environment) {
		//if it's source with mother
		if(mother!=null){
			//if mother exists
			if(environment.getAgents().containsKey(mother)){
				WaveBody bodymother = ((WaveBody) environment.getAgents().get(mother).getBody());
				//if the contact point it don't touch by the mother wave
				if(bodymother.getKillLittleCircle()>this.getBody().getPosition().distance(bodymother.getCenter())){
					this.active = false;
				}
			}
			//else
			else{
				this.active = false;
			}
			
		}
		//if source don't hit the water
		if(nbHit == 0){
			body.setInfluence(new GenerateInfluence(body.getID(),body.getFrequency(),body.getAmplitude(),body.getPosition(),beginRadius));
			nbHit++;
			return true;
		}
		//else
		else{
			//if the wave is active and it hit less of 50, it stay
			if(this.active == true && nbHit<50){
				body.setInfluence(new RestInfluence(body.getID()));
				if((currentTime-lastHitTime) > 1/this.body.getFrequency()){
					this.lastHitTime = currentTime;
					nbHit++;
				}
				
				return true;
			}
			//else kill itself
			else{
				this.active = false;
				body.setInfluence(new KillInfluence(body.getID()));
				return true;
			}
		}
	}

	@Override
	public AgentBody getBody() {
		return body;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getBegin_Radius() {
		return beginRadius;
	}

	public void setBegin_Radius(int begin_Radius) {
		this.beginRadius = begin_Radius;
	}

	public UUID getMother() {
		return mother;
	}

	public void setMother(UUID mother) {
		this.mother = mother;
	}
	
	

}
