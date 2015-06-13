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
	
	public Source(float freq, float amp, Point2f pos){
		this.body = new SourceBody(freq,amp,pos);
		this.active = true;
		this.nbHit = 0;
	}
	
	public Source(WaveBody mother, Point2f contactPoint){
		this.setMother(mother.getID());
		this.body = new SourceBody(mother.getFrequency(),mother.getAmplitude(),contactPoint);
		this.active = true;
		this.nbHit = 0;
	}
	
	public Source(WaveBody mother, Point2f contactPoint,int begin_Radius){
		this.setMother(mother.getID());
		this.body = new SourceBody(mother.getFrequency(),mother.getAmplitude()/2,contactPoint);
		this.active = true;
		this.nbHit = 0;
		this.beginRadius = begin_Radius;
	}
	@Override
	public boolean decide(float currentTime, Environment environment) {
		if(mother!=null){
			if(environment.getAgents().containsKey(mother)){
				WaveBody bodymother = ((WaveBody) environment.getAgents().get(mother).getBody());
				if(bodymother.getKillLittleCircle()>this.getBody().getPosition().distance(bodymother.getCenter())){
					this.active = false;
				}
			}
			else{
				this.active = false;
			}
			
		}
		
		if(nbHit == 0){
			body.setInfluence(new GenerateInfluence(body.getID(),body.getFrequency(),body.getAmplitude(),body.getPosition(),beginRadius));
			nbHit++;
			return true;
		}
		else{
			if(this.active == true && nbHit<50){
				body.setInfluence(new RestInfluence(body.getID()));
				if((currentTime-lastHitTime) > 1/this.body.getFrequency()){
					this.lastHitTime = currentTime;
					nbHit++;
				}
				
				return true;
			}
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
